package org.iesharia.senderismolanzarote.domain.repository.route.main

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.main.Route
import java.math.BigDecimal

interface RouteRepository {
    fun getAllRoutes(): Flow<List<Route>>
    suspend fun getRouteById(routeId: Int): Route?
    fun getRoutesByPreferences(
        maxDifficulty: Int,
        maxDistance: BigDecimal,
        maxDurationMinutes: Int
    ): Flow<List<Route>>
    suspend fun insertRoute(route: Route)
    suspend fun updateRoute(route: Route)
    suspend fun deleteRoute(route: Route)
}