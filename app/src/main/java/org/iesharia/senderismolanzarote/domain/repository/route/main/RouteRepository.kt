package org.iesharia.senderismolanzarote.domain.repository.route.main

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import java.math.BigDecimal

interface RouteRepository {
    fun getAllRoutes(): Flow<List<RouteModel>>
    suspend fun getRouteById(routeId: Int): RouteModel?
    fun getRoutesByPreferences(
        maxDifficulty: Int,
        maxDistance: BigDecimal,
        maxDurationMinutes: Int
    ): Flow<List<RouteModel>>
    suspend fun insertRoute(routeModel: RouteModel)
    suspend fun updateRoute(routeModel: RouteModel)
    suspend fun deleteRoute(routeModel: RouteModel)
}