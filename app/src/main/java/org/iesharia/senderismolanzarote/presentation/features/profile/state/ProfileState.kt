package org.iesharia.senderismolanzarote.presentation.features.profile.state

import org.iesharia.senderismolanzarote.domain.model.route.main.ActivityRecordModel
import org.iesharia.senderismolanzarote.domain.model.user.FavoriteRouteModel
import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState

data class ProfileUiState(
    val profile: UiState<ProfileData> = UiState.Initial,
    val isEditMode: Boolean = false
)

data class ProfileData(
    val user: UserModel,
    val preferences: UserPreferencesModel?,
    val activities: List<ActivityRecordModel>,
    val favorites: List<FavoriteRouteModel>
)