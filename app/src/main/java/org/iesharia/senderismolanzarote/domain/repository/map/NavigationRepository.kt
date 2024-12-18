package org.iesharia.senderismolanzarote.domain.repository.map

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.map.NavigationState
import org.iesharia.senderismolanzarote.domain.model.map.MapSettings
import java.math.BigDecimal

interface NavigationRepository {
    fun getNavigationState(): Flow<NavigationState>
    fun getMapSettings(): Flow<MapSettings>
    suspend fun startNavigation(routeId: Int)
    suspend fun stopNavigation()
    suspend fun toggleMapMode()
    suspend fun updateUserLocation(latitude: BigDecimal, longitude: BigDecimal)
}