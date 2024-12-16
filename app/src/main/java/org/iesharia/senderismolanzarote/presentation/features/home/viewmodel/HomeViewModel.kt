package org.iesharia.senderismolanzarote.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.domain.repository.route.main.RouteRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserPreferencesRepository
import org.iesharia.senderismolanzarote.presentation.home.state.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routeRepository: RouteRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRoutes()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadRoutes() {
        viewModelScope.launch {
            // Cargar todas las rutas
            _uiState.update { it.copy(allRoutes = AllRoutesState.Loading) }
            try {
                routeRepository.getAllRoutes()
                    .collect { routes ->
                        _uiState.update {
                            it.copy(allRoutes = AllRoutesState.Success(routes))
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(allRoutes = AllRoutesState.Error(e.message ?: "Error desconocido"))
                }
            }

            // Cargar rutas sugeridas basadas en preferencias
            _uiState.update { it.copy(suggestedRoutes = SuggestedRoutesState.Loading) }
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
                            it.copy(suggestedRoutes = SuggestedRoutesState.Success(routes))
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(suggestedRoutes = SuggestedRoutesState.Error(e.message ?: "Error desconocido"))
                }
            }
        }
    }

    fun setActiveRoute(routeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(activeRoute = ActiveRouteState.Loading) }
            try {
                val route = routeRepository.getRouteById(routeId)
                if (route != null) {
                    _uiState.update { it.copy(activeRoute = ActiveRouteState.Success(route)) }
                } else {
                    _uiState.update {
                        it.copy(activeRoute = ActiveRouteState.Error("Ruta no encontrada"))
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(activeRoute = ActiveRouteState.Error(e.message ?: "Error desconocido"))
                }
            }
        }
    }

    fun clearActiveRoute() {
        _uiState.update { it.copy(activeRoute = ActiveRouteState.None) }
    }

    fun refreshRoutes() {
        loadRoutes()
    }
}