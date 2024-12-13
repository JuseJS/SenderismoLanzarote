package org.iesharia.senderismolanzarote.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import org.iesharia.senderismolanzarote.domain.repository.user.UserRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserRoleRepository
import javax.inject.Inject
import at.favre.lib.crypto.bcrypt.BCrypt

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Initial)
    val registerState = _registerState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val user = userRepository.getUserByEmail(email)
                if (user != null && verifyPassword(password, user.passwordHash)) {
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
                val hashedPassword = hashPassword(password)
                val newUser = UserModel(
                    username = username,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    roleId = 1
                )
                val userId = userRepository.insertUser(newUser)
                if (userId > 0) {
                    _registerState.value = RegisterState.Success
                } else {
                    _registerState.value = RegisterState.Error("Error al crear usuario")
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    private fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
    }
}