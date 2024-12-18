package org.iesharia.senderismolanzarote.presentation.features.navigation.state

import org.iesharia.senderismolanzarote.domain.model.map.NavigationState
import org.iesharia.senderismolanzarote.domain.model.map.MapSettings
import org.iesharia.senderismolanzarote.domain.model.route.main.PointOfInterestModel

data class NavigationUiState(
    val navigationState: NavigationState = NavigationState(),
    val mapSettings: MapSettings = MapSettings(),
    val pointsOfInterest: List<PointOfInterestModel> = emptyList(),
    val error: String? = null
)