package org.iesharia.senderismolanzarote.domain.repository.route.main

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.main.PointOfInterest

interface PointOfInterestRepository {
    fun getRoutePointsOfInterest(routeId: Int): Flow<List<PointOfInterest>>
    suspend fun insertPointOfInterest(pointOfInterest: PointOfInterest)
    suspend fun updatePointOfInterest(pointOfInterest: PointOfInterest)
    suspend fun deletePointOfInterest(pointOfInterest: PointOfInterest)
}