package org.iesharia.senderismolanzarote.presentation.profile.state

import org.iesharia.senderismolanzarote.domain.model.route.main.ActivityRecordModel
import org.iesharia.senderismolanzarote.domain.model.user.FavoriteRouteModel
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel
import org.iesharia.senderismolanzarote.domain.model.user.UserModel

data class ProfileUiState(
    val userState: UserProfileState = UserProfileState.Initial,
    val preferencesState: PreferencesState = PreferencesState.Initial,
    val activitiesState: ActivitiesState = ActivitiesState.Initial,
    val favoritesState: FavoritesState = FavoritesState.Initial,
    val themeState: ThemeState = ThemeState.System
)

sealed interface UserProfileState {
    object Initial : UserProfileState
    object Loading : UserProfileState
    data class Success(val user: UserModel) : UserProfileState
    data class Error(val message: String) : UserProfileState
}

sealed interface PreferencesState {
    object Initial : PreferencesState
    object Loading : PreferencesState
    data class Success(val preferences: UserPreferencesModel) : PreferencesState
    data class Error(val message: String) : PreferencesState
}

sealed interface ActivitiesState {
    object Initial : ActivitiesState
    object Loading : ActivitiesState
    data class Success(val activities: List<ActivityRecordModel>) : ActivitiesState
    data class Error(val message: String) : ActivitiesState
}

sealed interface FavoritesState {
    object Initial : FavoritesState
    object Loading : FavoritesState
    data class Success(val favorites: List<FavoriteRouteModel>) : FavoritesState
    data class Error(val message: String) : FavoritesState
}

sealed interface ThemeState {
    object System : ThemeState
    object Light : ThemeState
    object Dark : ThemeState
}