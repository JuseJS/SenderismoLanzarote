package org.iesharia.senderismolanzarote.presentation.features.home.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.data.handler.ErrorHandler
import org.iesharia.senderismolanzarote.data.logger.ErrorLogger
import org.iesharia.senderismolanzarote.data.mapper.error.toErrorModel
import org.iesharia.senderismolanzarote.data.repository.auth.AuthRepository
import org.iesharia.senderismolanzarote.domain.model.user.FavoriteRouteModel
import org.iesharia.senderismolanzarote.domain.repository.map.NavigationRepository
import org.iesharia.senderismolanzarote.domain.repository.route.main.PointOfInterestRepository
import org.iesharia.senderismolanzarote.domain.repository.route.main.RouteRepository
import org.iesharia.senderismolanzarote.domain.repository.user.FavoriteRouteRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserPreferencesRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserRepository
import org.iesharia.senderismolanzarote.presentation.core.base.BaseViewModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState
import org.iesharia.senderismolanzarote.presentation.features.home.state.HomeUiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routeRepository: RouteRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val pointOfInterestRepository: PointOfInterestRepository,
    private val favoriteRouteRepository: FavoriteRouteRepository,
    private val navigationRepository: NavigationRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    errorHandler: ErrorHandler,
    errorLogger: ErrorLogger
) : BaseViewModel(errorHandler, errorLogger) {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadInitialData()
        observeFavorites()
        observeNavigation()
    }

    fun loadInitialData() {
        loadAllRoutes()
        loadSuggestedRoutes()
    }

    fun loadAllRoutes() {
        viewModelScope.launch {
            _uiState.update { it.copy(allRoutes = UiState.Loading) }
            try {
                routeRepository.getAllRoutes().collectLatest { routes ->
                    _uiState.update { it.copy(allRoutes = UiState.Success(routes)) }
                }
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
                _uiState.update { it.copy(allRoutes = UiState.Error(error.message)) }
            }
        }
    }

    fun loadSuggestedRoutes() {
        viewModelScope.launch {
            _uiState.update { it.copy(suggestedRoutes = UiState.Loading) }
            try {
                val userId = authRepository.getCurrentUserId()
                val preferences = userPreferencesRepository.getUserPreferences(userId)

                if (preferences != null) {
                    routeRepository.getRoutesByPreferences(
                        maxDifficulty = preferences.preferredDifficultyLevel.id,
                        maxDistance = preferences.maxDistanceKm,
                        maxDurationMinutes = preferences.maxDurationMinutes
                    ).collectLatest { routes ->
                        _uiState.update {
                            it.copy(suggestedRoutes = UiState.Success(routes))
                        }
                    }
                } else {
                    _uiState.update { it.copy(suggestedRoutes = UiState.Success(emptyList())) }
                }
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
                _uiState.update { it.copy(suggestedRoutes = UiState.Error(error.message)) }
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            try {
                val userId = authRepository.getCurrentUserId()
                favoriteRouteRepository.observeFavoriteRoutes(userId)
                    .collect { favorites ->
                        _uiState.update { it.copy(
                            favoriteRoutes = UiState.Success(favorites.map { it.routeModel }),
                            routesFavoriteStatus = favorites.map { it.routeModel.id }.toSet()
                        )}
                    }
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
                _uiState.update { it.copy(
                    favoriteRoutes = UiState.Error(error.message)
                )}
            }
        }
    }

    private fun observeNavigation() {
        viewModelScope.launch {
            navigationRepository.getNavigationState().collect { navState ->
                _uiState.update { it.copy(navigationState = navState) }
            }
        }
    }

    fun selectRoute(routeId: Int) {
        viewModelScope.launch {
            try {
                val route = routeRepository.getRouteById(routeId)
                    ?: throw Exception("Ruta no encontrada")

                _uiState.update { it.copy(
                    selectedRoute = UiState.Success(route)
                )}

                loadPointsOfInterest(routeId)
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
                _uiState.update { it.copy(
                    selectedRoute = UiState.Error(error.message)
                )}
            }
        }
    }

    private fun loadPointsOfInterest(routeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(pointsOfInterest = UiState.Loading) }
            try {
                pointOfInterestRepository.getRoutePointsOfInterest(routeId)
                    .collectLatest { points ->
                        Log.d("HomeViewModel", "POIs cargados: ${points.size}")
                        _uiState.update { it.copy(pointsOfInterest = UiState.Success(points)) }
                    }
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
                _uiState.update { it.copy(pointsOfInterest = UiState.Error(error.message)) }
            }
        }
    }

    fun clearSelectedRoute() {
        _uiState.update { it.copy(
            selectedRoute = UiState.Initial,
            pointsOfInterest = UiState.Initial
        )}
    }

    fun toggleFavorite(routeId: Int) {
        viewModelScope.launch {
            try {
                val userId = authRepository.getCurrentUserId()
                val route = routeRepository.getRouteById(routeId)
                    ?: throw Exception("Ruta no encontrada")
                val user = userRepository.getUserById(userId)
                    ?: throw Exception("Usuario no encontrado")

                val favoriteModel = FavoriteRouteModel(
                    userModel = user,
                    routeModel = route
                )

                if (routeId in (_uiState.value.routesFavoriteStatus)) {
                    favoriteRouteRepository.deleteFavoriteRoute(favoriteModel)
                } else {
                    favoriteRouteRepository.insertFavoriteRoute(favoriteModel)
                }
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
            }
        }
    }

    fun startRoute(routeId: Int) {
        viewModelScope.launch {
            try {
                loadPointsOfInterest(routeId)
                navigationRepository.startNavigation(routeId)
                clearSelectedRoute()
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
            }
        }
    }

    fun stopRoute() {
        viewModelScope.launch {
            try {
                navigationRepository.stopNavigation()
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
            }
        }
    }
}