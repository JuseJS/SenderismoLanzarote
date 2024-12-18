package org.iesharia.senderismolanzarote.data.repository.map

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.iesharia.senderismolanzarote.domain.model.map.NavigationState
import org.iesharia.senderismolanzarote.domain.model.map.MapSettings
import org.iesharia.senderismolanzarote.domain.repository.map.NavigationRepository
import org.iesharia.senderismolanzarote.domain.repository.route.main.RouteRepository
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationRepositoryImpl @Inject constructor(
    private val routeRepository: RouteRepository
) : NavigationRepository {
    private val _navigationState = MutableStateFlow(NavigationState())
    private val _mapSettings = MutableStateFlow(MapSettings())

    override fun getNavigationState(): Flow<NavigationState> = _navigationState.asStateFlow()
    override fun getMapSettings(): Flow<MapSettings> = _mapSettings.asStateFlow()

    override suspend fun startNavigation(routeId: Int) {
        val route = routeRepository.getRouteById(routeId)
        _navigationState.value = _navigationState.value.copy(
            isNavigating = true,
            currentRoute = route
        )
    }

    override suspend fun stopNavigation() {
        _navigationState.value = _navigationState.value.copy(
            isNavigating = false,
            currentRoute = null
        )
    }

    override suspend fun toggleMapMode() {
        _mapSettings.value = _mapSettings.value.copy(
            isNightMode = !_mapSettings.value.isNightMode
        )
    }

    override suspend fun updateUserLocation(latitude: BigDecimal, longitude: BigDecimal) {
        _navigationState.value = _navigationState.value.copy(
            userLatitude = latitude,
            userLongitude = longitude
        )
    }
}