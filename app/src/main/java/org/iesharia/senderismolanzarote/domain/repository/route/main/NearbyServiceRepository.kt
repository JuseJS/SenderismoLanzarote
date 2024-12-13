package org.iesharia.senderismolanzarote.domain.repository.route.main

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.main.NearbyServiceModel

interface NearbyServiceRepository {
    fun getRouteNearbyServices(routeId: Int): Flow<List<NearbyServiceModel>>
    suspend fun insertNearbyService(nearbyServiceModel: NearbyServiceModel)
    suspend fun updateNearbyService(nearbyServiceModel: NearbyServiceModel)
    suspend fun deleteNearbyService(nearbyServiceModel: NearbyServiceModel)
}