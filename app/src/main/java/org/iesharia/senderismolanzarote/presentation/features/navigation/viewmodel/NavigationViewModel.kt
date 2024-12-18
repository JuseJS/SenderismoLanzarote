package org.iesharia.senderismolanzarote.presentation.features.navigation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.data.handler.ErrorHandler
import org.iesharia.senderismolanzarote.data.logger.ErrorLogger
import org.iesharia.senderismolanzarote.domain.repository.map.NavigationRepository
import org.iesharia.senderismolanzarote.domain.repository.route.main.PointOfInterestRepository
import org.iesharia.senderismolanzarote.presentation.core.base.BaseViewModel
import org.iesharia.senderismolanzarote.presentation.features.navigation.state.NavigationUiState
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val navigationRepository: NavigationRepository,
    private val pointOfInterestRepository: PointOfInterestRepository,
    errorHandler: ErrorHandler,
    errorLogger: ErrorLogger
) : BaseViewModel(errorHandler, errorLogger) {

    private val _uiState = MutableStateFlow(NavigationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeNavigationState()
    }

    private fun observeNavigationState() {
        viewModelScope.launch {
            combine(
                navigationRepository.getNavigationState(),
                navigationRepository.getMapSettings()
            ) { navState, mapSettings ->
                val pois = navState.currentRoute?.let { route ->
                    pointOfInterestRepository.getRoutePointsOfInterest(route.id).first()
                } ?: emptyList()

                NavigationUiState(
                    navigationState = navState,
                    mapSettings = mapSettings,
                    pointsOfInterest = pois
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun toggleMapMode() {
        viewModelScope.launch {
            try {
                navigationRepository.toggleMapMode()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun stopNavigation() {
        viewModelScope.launch {
            navigationRepository.stopNavigation()
        }
    }
}