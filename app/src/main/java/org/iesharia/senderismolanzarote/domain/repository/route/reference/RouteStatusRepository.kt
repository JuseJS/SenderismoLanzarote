package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.RouteStatusModel

interface RouteStatusRepository {
    fun getAllRouteStatuses(): Flow<List<RouteStatusModel>>
    suspend fun getRouteStatusById(id: Int): RouteStatusModel?
    suspend fun insertRouteStatus(routeStatus: RouteStatusModel)
}