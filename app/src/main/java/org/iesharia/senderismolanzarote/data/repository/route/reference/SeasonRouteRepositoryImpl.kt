package org.iesharia.senderismolanzarote.data.repository.route.reference

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.SeasonRouteDao
import org.iesharia.senderismolanzarote.data.mapper.route.reference.toSeasonRoute
import org.iesharia.senderismolanzarote.domain.model.route.reference.SeasonRouteModel
import org.iesharia.senderismolanzarote.domain.repository.route.reference.SeasonRouteRepository
import javax.inject.Inject

class SeasonRouteRepositoryImpl @Inject constructor(
    private val seasonRouteDao: SeasonRouteDao
) : SeasonRouteRepository {

    override fun getAllSeasonRoutes(): Flow<List<SeasonRouteModel>> {
        return seasonRouteDao.getAllSeasonRoutes().map { entities ->
            entities.map { it.toSeasonRoute() }
        }
    }

    override suspend fun getSeasonRouteById(id: Int): SeasonRouteModel? {
        return seasonRouteDao.getSeasonRouteById(id)?.toSeasonRoute()
    }
}