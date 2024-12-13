package org.iesharia.senderismolanzarote.domain.repository.route.main

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.main.PointOfInterestModel

interface PointOfInterestRepository {
    fun getRoutePointsOfInterest(routeId: Int): Flow<List<PointOfInterestModel>>
    suspend fun insertPointOfInterest(pointOfInterestModel: PointOfInterestModel)
    suspend fun updatePointOfInterest(pointOfInterestModel: PointOfInterestModel)
    suspend fun deletePointOfInterest(pointOfInterestModel: PointOfInterestModel)
}