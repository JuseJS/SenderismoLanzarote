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
import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel
import org.iesharia.senderismolanzarote.domain.repository.route.main.ActivityRecordRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.DifficultyLevelRepository
import org.iesharia.senderismolanzarote.domain.repository.user.*
import org.iesharia.senderismolanzarote.presentation.core.base.BaseViewModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState
import org.iesharia.senderismolanzarote.presentation.features.profile.state.ProfileData
import org.iesharia.senderismolanzarote.presentation.features.profile.state.ProfileUiState
import java.math.BigDecimal
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

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _difficultyLevels = mutableStateListOf<DifficultyLevelModel>()
    val difficultyLevels: List<DifficultyLevelModel> = _difficultyLevels

    private var currentProfileData: ProfileData? = null
    private var pendingPreferencesUpdate: UserPreferencesModel? = null

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(profile = UiState.Loading) }

                val difficulties = difficultyLevelRepository.getAllDifficultyLevels().first()
                _difficultyLevels.clear()
                _difficultyLevels.addAll(difficulties)

                val userId = authRepository.getCurrentUserId()
                val user = userRepository.getUserById(userId)
                    ?: throw Exception("Usuario no encontrado")

                val preferences = loadOrCreatePreferences(userId, user)

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
                val error = e.toErrorModel()
                errorLogger.logError(error)
                _uiState.update { it.copy(profile = UiState.Error(error.message)) }
            }
        }
    }

    private suspend fun loadOrCreatePreferences(
        userId: Int,
        user: UserModel
    ): UserPreferencesModel {
        return userPreferencesRepository.getUserPreferences(userId) ?: createDefaultPreferences(user)
    }

    private suspend fun createDefaultPreferences(user: UserModel): UserPreferencesModel {
        val defaultDifficulty = _difficultyLevels.minByOrNull { it.id }
            ?: throw Exception("No hay niveles de dificultad disponibles")

        return UserPreferencesModel(
            userModel = user,
            preferredDifficultyLevel = defaultDifficulty,
            maxDistanceKm = BigDecimal("10.0"),
            maxDurationMinutes = 120
        ).also {
            userPreferencesRepository.insertUserPreferences(it)
        }
    }

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditMode = !it.isEditMode) }
    }

    fun updatePreferences(preferences: UserPreferencesModel) {
        pendingPreferencesUpdate = preferences
        currentProfileData = currentProfileData?.copy(preferences = preferences)
        _uiState.update {
            it.copy(profile = UiState.Success(
                currentProfileData ?: throw IllegalStateException("No profile data available")
            ))
        }
    }

    fun saveChanges() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(profile = UiState.Loading) }

                pendingPreferencesUpdate?.let { preferences ->
                    try {
                        userPreferencesRepository.updateUserPreferences(preferences)
                    } catch (e: Exception) {
                        // Si falla la actualización, intentar insertar
                        userPreferencesRepository.insertUserPreferences(preferences)
                    }
                }

                loadInitialData()

                _uiState.update { it.copy(isEditMode = false) }
                pendingPreferencesUpdate = null

            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
                _uiState.update { it.copy(profile = UiState.Error(error.message)) }
            }
        }
    }

    fun cancelChanges() {
        viewModelScope.launch {
            pendingPreferencesUpdate = null
            loadInitialData()
            _uiState.update { it.copy(isEditMode = false) }
        }
    }

    fun reloadProfile() {
        loadInitialData()
    }

    override fun onCleared() {
        super.onCleared()
        pendingPreferencesUpdate = null
        currentProfileData = null
        _difficultyLevels.clear()
    }
}