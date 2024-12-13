package org.iesharia.senderismolanzarote.data.repository.route.reference

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.RouteStatusDao
import org.iesharia.senderismolanzarote.data.mapper.route.reference.toRouteStatus
import org.iesharia.senderismolanzarote.domain.model.route.reference.RouteStatusModel
import org.iesharia.senderismolanzarote.domain.repository.route.reference.RouteStatusRepository
import javax.inject.Inject

class RouteStatusRepositoryImpl @Inject constructor(
    private val routeStatusDao: RouteStatusDao
) : RouteStatusRepository {

    override fun getAllRouteStatuses(): Flow<List<RouteStatusModel>> {
        return routeStatusDao.getAllRouteStatuses().map { entities ->
            entities.map { it.toRouteStatus() }
        }
    }

    override suspend fun getRouteStatusById(id: Int): RouteStatusModel? {
        return routeStatusDao.getRouteStatusById(id)?.toRouteStatus()
    }
}