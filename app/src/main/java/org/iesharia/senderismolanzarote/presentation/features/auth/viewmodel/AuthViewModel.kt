package org.iesharia.senderismolanzarote.presentation.features.auth.viewmodel

import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.data.handler.ErrorHandler
import org.iesharia.senderismolanzarote.data.logger.ErrorLogger
import org.iesharia.senderismolanzarote.data.mapper.error.toErrorModel
import org.iesharia.senderismolanzarote.data.repository.auth.AuthRepository
import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import org.iesharia.senderismolanzarote.domain.repository.user.UserRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserRoleRepository
import org.iesharia.senderismolanzarote.presentation.core.base.BaseViewModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState
import org.iesharia.senderismolanzarote.presentation.features.auth.state.AuthUiState
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    errorHandler: ErrorHandler,
    errorLogger: ErrorLogger
) : BaseViewModel(errorHandler, errorLogger) {

    private val _authState = MutableStateFlow<UiState<UserModel>>(UiState.Initial)
    private val _registerState = MutableStateFlow<UiState<Unit>>(UiState.Initial)
    private val _isAuthLoading = MutableStateFlow(true)
    private val _logoutComplete = MutableStateFlow(false)
    private val _isLogin = MutableStateFlow(true)

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    val isAuthLoading = _isAuthLoading.asStateFlow()
    val logoutComplete = _logoutComplete.asStateFlow()
    val isAuthenticated = authRepository.isAuthenticated

    init {
        observeAuthStates()
        checkExistingSession()
    }

    private fun observeAuthStates() {
        viewModelScope.launch {
            combine(
                _authState,
                _registerState,
                _isLogin
            ) { authState, registerState, isLogin ->
                AuthUiState(
                    isLogin = isLogin,
                    authState = authState,
                    registerState = registerState
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    private fun checkExistingSession() {
        handleLoadOperation(_authState) {
            _isAuthLoading.value = true
            try {
                authRepository.currentSession.first()?.let { session ->
                    if (authRepository.validateSession(session.token)) {
                        userRepository.getUserById(session.userId)
                            ?: throw Exception("Usuario no encontrado")
                    } else {
                        authRepository.clearAuthSession()
                        null
                    }
                }
            } finally {
                _isAuthLoading.value = false
            } as UserModel
        }
    }

    fun login(email: String, password: String) {
        handleLoadOperation(_authState) {
            val user = userRepository.getUserByEmail(email)
                ?: throw Exception("Credenciales inválidas")

            if (!verifyPassword(password, user.passwordHash)) {
                throw Exception("Credenciales inválidas")
            }

            val sessionToken = UUID.randomUUID().toString()
            authRepository.saveAuthSession(user.id, sessionToken)
            user
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        handleLoadOperation(_registerState) {
            userRepository.getUserByEmail(email)?.let {
                throw Exception("El email ya está registrado")
            }

            val userRole = userRoleRepository.getUserRoleById(1)
                ?: throw Exception("Role no encontrado")

            val hashedPassword = hashPassword(password)
            val newUser = UserModel(
                username = username,
                email = email,
                firstName = firstName,
                lastName = lastName,
                roleModel = userRole,
                passwordHash = hashedPassword
            )

            val userId = userRepository.insertUser(newUser)
            if (userId > 0) {
                val sessionToken = UUID.randomUUID().toString()
                authRepository.saveAuthSession(userId.toInt(), sessionToken)
            } else {
                throw Exception("Error al crear usuario")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                _logoutComplete.value = false
                authRepository.clearAuthSession()
                resetStates()
                _logoutComplete.value = true
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
            }
        }
    }

    private fun resetStates() {
        _authState.value = UiState.Initial
        _registerState.value = UiState.Initial
        _isLogin.value = true
    }

    fun resetLogout() {
        _logoutComplete.value = false
    }

    fun clearError() {
        _authState.value = UiState.Initial
        _registerState.value = UiState.Initial
    }

    fun setLoginMode(isLogin: Boolean) {
        _isLogin.value = isLogin
        clearError()
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    private fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
    }
}