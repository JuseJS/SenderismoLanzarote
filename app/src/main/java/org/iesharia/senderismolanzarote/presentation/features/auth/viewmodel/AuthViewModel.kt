package org.iesharia.senderismolanzarote.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.data.repository.auth.AuthRepository
import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import org.iesharia.senderismolanzarote.domain.repository.user.UserRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserRoleRepository
import org.iesharia.senderismolanzarote.presentation.auth.state.AuthState
import org.iesharia.senderismolanzarote.presentation.auth.state.RegisterState
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRoleRepository: UserRoleRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Initial)
    val registerState = _registerState.asStateFlow()

    val isAuthenticated = authRepository.isAuthenticated

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val user = userRepository.getUserByEmail(email)
                if (user != null && verifyPassword(password, user.passwordHash)) {
                    // Generar un token de sesión simple (en producción usarías JWT u otro sistema seguro)
                    val sessionToken = UUID.randomUUID().toString()
                    authRepository.saveAuthSession(user.id, sessionToken)
                    _authState.value = AuthState.Success(user)
                } else {
                    _authState.value = AuthState.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido")
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
            _registerState.value = RegisterState.Loading
            try {
                // Verificar si el email ya está registrado
                val existingUser = userRepository.getUserByEmail(email)
                if (existingUser != null) {
                    _registerState.value = RegisterState.Error("El email ya está registrado")
                    return@launch
                }

                val userRole = userRoleRepository.getUserRoleById(1) // Role por defecto
                if (userRole == null) {
                    _registerState.value = RegisterState.Error("Role no encontrado")
                    return@launch
                }

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
                    // Crear una sesión automáticamente después del registro
                    val sessionToken = UUID.randomUUID().toString()
                    authRepository.saveAuthSession(userId.toInt(), sessionToken)
                    _registerState.value = RegisterState.Success
                } else {
                    _registerState.value = RegisterState.Error("Error al crear usuario")
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.clearAuthSession()
            _authState.value = AuthState.Initial
        }
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    private fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
    }
}