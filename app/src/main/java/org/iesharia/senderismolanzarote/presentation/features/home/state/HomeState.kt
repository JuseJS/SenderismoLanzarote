package org.iesharia.senderismolanzarote.presentation.features.home.state

import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState

data class HomeUiState(
    val suggestedRoutes: UiState<List<RouteModel>> = UiState.Initial,
    val allRoutes: UiState<List<RouteModel>> = UiState.Initial,
    val activeRoute: UiState<RouteModel?> = UiState.Initial
)