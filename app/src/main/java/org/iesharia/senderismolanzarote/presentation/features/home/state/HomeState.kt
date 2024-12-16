package org.iesharia.senderismolanzarote.presentation.home.state

import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel

data class HomeUiState(
    val suggestedRoutes: SuggestedRoutesState = SuggestedRoutesState.Initial,
    val allRoutes: AllRoutesState = AllRoutesState.Initial,
    val activeRoute: ActiveRouteState = ActiveRouteState.None
)

sealed interface SuggestedRoutesState {
    object Initial : SuggestedRoutesState
    object Loading : SuggestedRoutesState
    data class Success(val routes: List<RouteModel>) : SuggestedRoutesState
    data class Error(val message: String) : SuggestedRoutesState
}

sealed interface AllRoutesState {
    object Initial : AllRoutesState
    object Loading : AllRoutesState
    data class Success(val routes: List<RouteModel>) : AllRoutesState
    data class Error(val message: String) : AllRoutesState
}

sealed interface ActiveRouteState {
    object None : ActiveRouteState
    object Loading : ActiveRouteState
    data class Success(val route: RouteModel) : ActiveRouteState
    data class Error(val message: String) : ActiveRouteState
}