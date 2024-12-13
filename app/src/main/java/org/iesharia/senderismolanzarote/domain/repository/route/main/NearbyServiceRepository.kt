package org.iesharia.senderismolanzarote.domain.repository.route.main

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.main.NearbyService

interface NearbyServiceRepository {
    fun getRouteNearbyServices(routeId: Int): Flow<List<NearbyService>>
    suspend fun insertNearbyService(nearbyService: NearbyService)
    suspend fun updateNearbyService(nearbyService: NearbyService)
    suspend fun deleteNearbyService(nearbyService: NearbyService)
}