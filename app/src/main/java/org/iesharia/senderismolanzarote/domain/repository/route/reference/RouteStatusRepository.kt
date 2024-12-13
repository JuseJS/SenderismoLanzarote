package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.RouteStatus

interface RouteStatusRepository {
    fun getAllRouteStatuses(): Flow<List<RouteStatus>>
    suspend fun getRouteStatusById(id: Int): RouteStatus?
}