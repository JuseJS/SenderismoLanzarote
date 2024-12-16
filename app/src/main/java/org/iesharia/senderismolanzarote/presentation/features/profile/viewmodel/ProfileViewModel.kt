package org.iesharia.senderismolanzarote.presentation.features.profile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.data.handler.ErrorHandler
import org.iesharia.senderismolanzarote.data.logger.ErrorLogger
import org.iesharia.senderismolanzarote.data.mapper.error.toErrorModel
import org.iesharia.senderismolanzarote.data.repository.auth.AuthRepository
import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel
import org.iesharia.senderismolanzarote.domain.repository.route.main.ActivityRecordRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.DifficultyLevelRepository
import org.iesharia.senderismolanzarote.domain.repository.user.*
import org.iesharia.senderismolanzarote.presentation.core.base.BaseViewModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState
import org.iesharia.senderismolanzarote.presentation.features.profile.state.ProfileData
import org.iesharia.senderismolanzarote.presentation.features.profile.state.ProfileUiState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val activityRecordRepository: ActivityRecordRepository,
    private val favoriteRouteRepository: FavoriteRouteRepository,
    private val difficultyLevelRepository: DifficultyLevelRepository,
    errorHandler: ErrorHandler,
    errorLogger: ErrorLogger
) : BaseViewModel(errorHandler, errorLogger) {

    private val _profileState = MutableStateFlow<UiState<ProfileData>>(UiState.Initial)
    private val _isEditMode = MutableStateFlow(false)
    private var currentProfileData: ProfileData? = null

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _difficultyLevels = mutableStateListOf<DifficultyLevelModel>()
    val difficultyLevels: List<DifficultyLevelModel> = _difficultyLevels

    init {
        observeProfileState()
        loadProfile()
        loadDifficultyLevels()
    }

    private fun observeProfileState() {
        viewModelScope.launch {
            combine(
                _profileState,
                _isEditMode
            ) { profileState, isEditMode ->
                ProfileUiState(
                    profile = profileState,
                    isEditMode = isEditMode
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun loadProfile() {
        handleLoadOperation(_profileState) {
            val userId = authRepository.getCurrentUserId()
            val user = userRepository.getUserById(userId)
                ?: throw Exception("Usuario no encontrado")
            val preferences = userPreferencesRepository.getUserPreferences(userId)
            val activities = activityRecordRepository.getUserActivityRecords(userId).first()
            val favorites = favoriteRouteRepository.getUserFavoriteRoutes(userId).first()

            ProfileData(
                user = user,
                preferences = preferences,
                activities = activities,
                favorites = favorites
            ).also {
                currentProfileData = it
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
                val error = e.toErrorModel()
                errorLogger.logError(error)
            }
        }
    }

    fun toggleEditMode() {
        _isEditMode.value = !_isEditMode.value
    }

    fun updatePreferences(preferences: UserPreferencesModel) {
        currentProfileData = currentProfileData?.copy(preferences = preferences)
    }

    fun saveChanges() {
        handleLoadOperation(_profileState) {
            currentProfileData?.let { profileData ->
                profileData.preferences?.let { preferences ->
                    userPreferencesRepository.updateUserPreferences(preferences)
                }
                userRepository.updateUser(profileData.user)
                _isEditMode.value = false
                profileData
            } ?: throw Exception("No hay datos para guardar")
        }
    }
}