package org.iesharia.senderismolanzarote.presentation.features.profile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.data.repository.auth.AuthRepository
import org.iesharia.senderismolanzarote.domain.model.route.main.ActivityRecordModel
import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel
import org.iesharia.senderismolanzarote.domain.repository.route.main.ActivityRecordRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.DifficultyLevelRepository
import org.iesharia.senderismolanzarote.domain.repository.user.*
import org.iesharia.senderismolanzarote.presentation.core.base.BaseViewModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState
import org.iesharia.senderismolanzarote.presentation.features.profile.state.ProfileUiState
import org.iesharia.senderismolanzarote.presentation.features.profile.state.ProfileData
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val activityRecordRepository: ActivityRecordRepository,
    private val favoriteRouteRepository: FavoriteRouteRepository,
    private val difficultyLevelRepository: DifficultyLevelRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private var _difficultyLevels = mutableStateListOf<DifficultyLevelModel>()
    val difficultyLevels: List<DifficultyLevelModel> = _difficultyLevels

    private var currentProfileData: ProfileData? = null

    init {
        loadProfile()
        loadDifficultyLevels()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(profile = UiState.Loading) }
            try {
                val userId = authRepository.getCurrentUserId()
                val user = userRepository.getUserById(userId)
                    ?: throw Exception("Usuario no encontrado")
                val preferences = userPreferencesRepository.getUserPreferences(userId)
                val activities = activityRecordRepository.getUserActivityRecords(userId).first()
                val favorites = favoriteRouteRepository.getUserFavoriteRoutes(userId).first()

                val profileData = ProfileData(
                    user = user,
                    preferences = preferences,
                    activities = activities,
                    favorites = favorites
                )
                currentProfileData = profileData
                _uiState.update { it.copy(profile = UiState.Success(profileData)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(profile = handleError<ProfileData>(e)) }
            }
        }
    }

    private fun loadDifficultyLevels() {
        viewModelScope.launch {
            try {
                difficultyLevelRepository.getAllDifficultyLevels()
                    .collect { levels ->
                        _difficultyLevels.clear()
                        _difficultyLevels.addAll(levels)
                    }
            } catch (e: Exception) {
                // Manejar error si es necesario
            }
        }
    }

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditMode = !it.isEditMode) }
    }

    fun updatePreferences(preferences: UserPreferencesModel) {
        currentProfileData = currentProfileData?.copy(preferences = preferences)
    }

    fun saveChanges() {
        viewModelScope.launch {
            _uiState.update { it.copy(profile = UiState.Loading) }
            try {
                currentProfileData?.let { profileData ->
                    profileData.preferences?.let { preferences ->
                        userPreferencesRepository.updateUserPreferences(preferences)
                    }
                    userRepository.updateUser(profileData.user)
                    _uiState.update {
                        it.copy(
                            profile = UiState.Success(profileData),
                            isEditMode = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(profile = handleError<ProfileData>(e)) }
            }
        }
    }
}