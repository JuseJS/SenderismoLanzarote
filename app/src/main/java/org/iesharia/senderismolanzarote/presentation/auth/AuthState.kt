package org.iesharia.senderismolanzarote.presentation.auth

import org.iesharia.senderismolanzarote.domain.model.user.UserModel

sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    data class Success(val user: UserModel) : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class RegisterState {
    object Initial : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}