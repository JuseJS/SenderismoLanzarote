package org.iesharia.senderismolanzarote.presentation.features.home.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.data.handler.ErrorHandler
import org.iesharia.senderismolanzarote.data.logger.ErrorLogger
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import org.iesharia.senderismolanzarote.domain.repository.route.main.RouteRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserPreferencesRepository
import org.iesharia.senderismolanzarote.presentation.core.base.BaseViewModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState
import org.iesharia.senderismolanzarote.presentation.features.home.state.HomeUiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routeRepository: RouteRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    errorHandler: ErrorHandler,
    errorLogger: ErrorLogger
) : BaseViewModel(errorHandler, errorLogger) {

    private val _allRoutes = MutableStateFlow<UiState<List<RouteModel>>>(UiState.Initial)
    private val _suggestedRoutes = MutableStateFlow<UiState<List<RouteModel>>>(UiState.Initial)
    private val _activeRoute = MutableStateFlow<UiState<RouteModel?>>(UiState.Initial)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRoutes()
        observeRoutes()
    }

    private fun observeRoutes() {
        viewModelScope.launch {
            combine(_allRoutes, _suggestedRoutes, _activeRoute) { allRoutes, suggestedRoutes, activeRoute ->
                HomeUiState(
                    allRoutes = allRoutes,
                    suggestedRoutes = suggestedRoutes,
                    activeRoute = activeRoute
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    private fun loadRoutes() {
        handleLoadOperation(_allRoutes) {
            routeRepository.getAllRoutes().first()
        }

        handleLoadOperation(_suggestedRoutes) {
            loadSuggestedRoutes()
        }
    }

    private suspend fun loadSuggestedRoutes(): List<RouteModel> {
        return userPreferencesRepository.getAllPreferences().first().let { preferences ->
            if (preferences.isEmpty()) {
                emptyList()
            } else {
                val userPrefs = preferences.first()
                routeRepository.getRoutesByPreferences(
                    maxDifficulty = userPrefs.preferredDifficultyLevel.id,
                    maxDistance = userPrefs.maxDistanceKm,
                    maxDurationMinutes = userPrefs.maxDurationMinutes
                ).first()
            }
        }
    }

    fun setActiveRoute(routeId: Int) {
        handleLoadOperation(_activeRoute) {
            routeRepository.getRouteById(routeId) ?: throw Exception("Ruta no encontrada")
        }
    }

    fun clearActiveRoute() {
        _activeRoute.value = UiState.Success(null)
    }

    fun refreshRoutes() {
        loadRoutes()
    }
}