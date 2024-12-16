package org.iesharia.senderismolanzarote.presentation.features.auth.state

import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState

data class AuthUiState(
    val isLogin: Boolean = true,
    val authState: UiState<UserModel> = UiState.Initial,
    val registerState: UiState<Unit> = UiState.Initial
)