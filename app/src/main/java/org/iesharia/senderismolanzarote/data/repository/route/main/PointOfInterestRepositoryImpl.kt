package org.iesharia.senderismolanzarote.data.repository.route.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.main.PointOfInterestDao
import org.iesharia.senderismolanzarote.data.mapper.route.main.toPointOfInterest
import org.iesharia.senderismolanzarote.data.mapper.route.main.toPointOfInterestEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.PointOfInterestModel
import org.iesharia.senderismolanzarote.domain.repository.route.main.PointOfInterestRepository
import org.iesharia.senderismolanzarote.domain.repository.route.main.RouteRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.PoiTypeRepository
import javax.inject.Inject

class PointOfInterestRepositoryImpl @Inject constructor(
    private val pointOfInterestDao: PointOfInterestDao,
    private val routeRepository: RouteRepository,
    private val poiTypeRepository: PoiTypeRepository
) : PointOfInterestRepository {

    override fun getRoutePointsOfInterest(routeId: Int): Flow<List<PointOfInterestModel>> {
        return pointOfInterestDao.getRoutePointsOfInterest(routeId).map { entities ->
            entities.mapNotNull { entity ->
                val route = routeRepository.getRouteById(entity.routeId)
                val poiType = poiTypeRepository.getPoiTypeById(entity.typeId)

                if (route != null && poiType != null) {
                    entity.toPointOfInterest(route, poiType)
                } else null
            }
        }
    }

    override suspend fun insertPointOfInterest(pointOfInterestModel: PointOfInterestModel) {
        pointOfInterestDao.insertPointOfInterest(pointOfInterestModel.toPointOfInterestEntity())
    }

    override suspend fun updatePointOfInterest(pointOfInterestModel: PointOfInterestModel) {
        pointOfInterestDao.updatePointOfInterest(pointOfInterestModel.toPointOfInterestEntity())
    }

    override suspend fun deletePointOfInterest(pointOfInterestModel: PointOfInterestModel) {
        pointOfInterestDao.deletePointOfInterest(pointOfInterestModel.toPointOfInterestEntity())
    }
}