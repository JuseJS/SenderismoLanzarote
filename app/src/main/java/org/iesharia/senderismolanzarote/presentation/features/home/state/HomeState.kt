package org.iesharia.senderismolanzarote.presentation.features.home.state

import org.iesharia.senderismolanzarote.domain.model.map.MapSettings
import org.iesharia.senderismolanzarote.domain.model.map.NavigationState
import org.iesharia.senderismolanzarote.domain.model.route.main.PointOfInterestModel
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState

data class HomeUiState(
    val suggestedRoutes: UiState<List<RouteModel>> = UiState.Initial,
    val favoriteRoutes: UiState<List<RouteModel>> = UiState.Initial,
    val allRoutes: UiState<List<RouteModel>> = UiState.Initial,
    val selectedRoute: UiState<RouteModel?> = UiState.Initial,
    val pointsOfInterest: UiState<List<PointOfInterestModel>> = UiState.Initial,
    val routesFavoriteStatus: Set<Int> = emptySet(),
    val navigationState: NavigationState = NavigationState(),
    val mapSettings: MapSettings = MapSettings()
)