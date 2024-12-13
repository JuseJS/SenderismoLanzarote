package org.iesharia.senderismolanzarote.data.repository.route.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.main.NearbyServiceDao
import org.iesharia.senderismolanzarote.data.mapper.route.main.toNearbyService
import org.iesharia.senderismolanzarote.data.mapper.route.main.toNearbyServiceEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.NearbyServiceModel
import org.iesharia.senderismolanzarote.domain.repository.route.main.NearbyServiceRepository
import org.iesharia.senderismolanzarote.domain.repository.route.main.RouteRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.ServiceTypeRepository
import javax.inject.Inject

class NearbyServiceRepositoryImpl @Inject constructor(
    private val nearbyServiceDao: NearbyServiceDao,
    private val routeRepository: RouteRepository,
    private val serviceTypeRepository: ServiceTypeRepository
) : NearbyServiceRepository {

    override fun getRouteNearbyServices(routeId: Int): Flow<List<NearbyServiceModel>> {
        return nearbyServiceDao.getRouteNearbyServices(routeId).map { entities ->
            entities.mapNotNull { entity ->
                val route = routeRepository.getRouteById(entity.routeId)
                val serviceType = serviceTypeRepository.getServiceTypeById(entity.serviceTypeId)

                if (route != null && serviceType != null) {
                    entity.toNearbyService(route, serviceType)
                } else null
            }
        }
    }

    override suspend fun insertNearbyService(nearbyServiceModel: NearbyServiceModel) {
        nearbyServiceDao.insertNearbyService(nearbyServiceModel.toNearbyServiceEntity())
    }

    override suspend fun updateNearbyService(nearbyServiceModel: NearbyServiceModel) {
        nearbyServiceDao.updateNearbyService(nearbyServiceModel.toNearbyServiceEntity())
    }

    override suspend fun deleteNearbyService(nearbyServiceModel: NearbyServiceModel) {
        nearbyServiceDao.deleteNearbyService(nearbyServiceModel.toNearbyServiceEntity())
    }
}