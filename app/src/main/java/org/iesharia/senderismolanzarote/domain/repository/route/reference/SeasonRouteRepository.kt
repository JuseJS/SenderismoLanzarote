package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.SeasonRouteModel

interface SeasonRouteRepository {
    fun getAllSeasonRoutes(): Flow<List<SeasonRouteModel>>
    suspend fun getSeasonRouteById(id: Int): SeasonRouteModel?
    suspend fun insertSeasonRoute(seasonRoute: SeasonRouteModel)
}
