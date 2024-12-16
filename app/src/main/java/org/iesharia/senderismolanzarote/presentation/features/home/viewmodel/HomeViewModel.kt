package org.iesharia.senderismolanzarote.presentation.features.home.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
    private val userPreferencesRepository: UserPreferencesRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRoutes()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadRoutes() {
        viewModelScope.launch {
            // Cargar todas las rutas
            _uiState.update { it.copy(allRoutes = UiState.Loading) }
            try {
                routeRepository.getAllRoutes()
                    .collect { routes ->
                        _uiState.update {
                            it.copy(allRoutes = UiState.Success(routes))
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(allRoutes = handleError<List<RouteModel>>(e))
                }
            }

            // Cargar rutas sugeridas
            _uiState.update { it.copy(suggestedRoutes = UiState.Loading) }
            try {
                userPreferencesRepository.getAllPreferences()
                    .flatMapLatest { preferences ->
                        if (preferences.isEmpty()) {
                            flowOf(emptyList())
                        } else {
                            val userPrefs = preferences.first()
                            routeRepository.getRoutesByPreferences(
                                maxDifficulty = userPrefs.preferredDifficultyLevel.id,
                                maxDistance = userPrefs.maxDistanceKm,
                                maxDurationMinutes = userPrefs.maxDurationMinutes
                            )
                        }
                    }
                    .collect { routes ->
                        _uiState.update {
                            it.copy(suggestedRoutes = UiState.Success(routes))
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(suggestedRoutes = handleError<List<RouteModel>>(e))
                }
            }
        }
    }

    fun setActiveRoute(routeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(activeRoute = UiState.Loading) }
            try {
                val route = routeRepository.getRouteById(routeId)
                _uiState.update {
                    it.copy(activeRoute = UiState.Success(route))
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(activeRoute = handleError<RouteModel?>(e))
                }
            }
        }
    }

    fun clearActiveRoute() {
        _uiState.update { it.copy(activeRoute = UiState.Success(null)) }
    }

    fun refreshRoutes() {
        loadRoutes()
    }
}