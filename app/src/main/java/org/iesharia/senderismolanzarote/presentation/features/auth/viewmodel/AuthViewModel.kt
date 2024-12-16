package org.iesharia.senderismolanzarote.presentation.features.auth.viewmodel

import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
    private val userRoleRepository: UserRoleRepository
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    private val _isAuthLoading = MutableStateFlow(true)
    val isAuthLoading = _isAuthLoading.asStateFlow()
    private val _logoutComplete = MutableStateFlow(false)
    val logoutComplete = _logoutComplete.asStateFlow()

    val isAuthenticated = authRepository.isAuthenticated

    init {
        // Verificar sesión existente
        viewModelScope.launch {
            _isAuthLoading.value = true
            authRepository.currentSession.collect { session ->
                session?.let {
                    try {
                        if (authRepository.validateSession(it.token)) {
                            val user = userRepository.getUserById(it.userId)
                            user?.let { validUser ->
                                _uiState.update { state ->
                                    state.copy(authState = UiState.Success(validUser))
                                }
                            }
                        } else {
                            authRepository.clearAuthSession()
                        }
                    } catch (e: Exception) {
                        handleAuthError(e)
                    }
                }
                _isAuthLoading.value = false
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(authState = UiState.Loading) }
            try {
                val user = userRepository.getUserByEmail(email)
                if (user != null && verifyPassword(password, user.passwordHash)) {
                    val sessionToken = UUID.randomUUID().toString()
                    authRepository.saveAuthSession(user.id, sessionToken)
                    _uiState.update {
                        it.copy(authState = UiState.Success(user))
                    }
                } else {
                    _uiState.update {
                        it.copy(authState = UiState.Error("Credenciales inválidas"))
                    }
                }
            } catch (e: Exception) {
                handleAuthError(e)
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(registerState = UiState.Loading) }
            try {
                // Verificar email existente
                userRepository.getUserByEmail(email)?.let {
                    _uiState.update {
                        it.copy(registerState = UiState.Error("El email ya está registrado"))
                    }
                    return@launch
                }

                // Obtener rol por defecto
                val userRole = userRoleRepository.getUserRoleById(1)
                    ?: throw Exception("Role no encontrado")

                // Crear nuevo usuario
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
                    // Crear sesión
                    val sessionToken = UUID.randomUUID().toString()
                    authRepository.saveAuthSession(userId.toInt(), sessionToken)
                    _uiState.update {
                        it.copy(registerState = UiState.Success(Unit))
                    }
                } else {
                    throw Exception("Error al crear usuario")
                }
            } catch (e: Exception) {
                handleAuthError(e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                _logoutComplete.value = false
                authRepository.clearAuthSession()
                _uiState.update { AuthUiState() }
                _logoutComplete.value = true
            } catch (e: Exception) {
                handleAuthError(e)
            }
        }
    }

    fun resetLogout() {
        _logoutComplete.value = false
    }

    fun clearError() {
        _uiState.update { currentState ->
            currentState.copy(
                authState = UiState.Initial,
                registerState = UiState.Initial
            )
        }
    }

    fun setLoginMode(isLogin: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isLogin = isLogin,
                authState = UiState.Initial,
                registerState = UiState.Initial
            )
        }
    }

    private fun handleAuthError(error: Exception) {
        _uiState.update {
            it.copy(
                authState = UiState.Error(error.message ?: "Error desconocido"),
                registerState = UiState.Initial
            )
        }
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    private fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
    }
}