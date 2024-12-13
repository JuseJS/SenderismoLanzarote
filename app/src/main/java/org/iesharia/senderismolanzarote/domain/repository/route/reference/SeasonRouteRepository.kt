package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.SeasonRoute

interface SeasonRouteRepository {
    fun getAllSeasonRoutes(): Flow<List<SeasonRoute>>
    suspend fun getSeasonRouteById(id: Int): SeasonRoute?
}
