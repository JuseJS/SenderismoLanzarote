package org.iesharia.senderismolanzarote.presentation.auth.state

import org.iesharia.senderismolanzarote.domain.model.user.UserModel

sealed interface AuthState {
    object Initial : AuthState
    object Loading : AuthState
    data class Success(val user: UserModel) : AuthState
    data class Error(val message: String) : AuthState
}

sealed interface RegisterState {
    object Initial : RegisterState
    object Loading : RegisterState
    object Success : RegisterState
    data class Error(val message: String) : RegisterState
}