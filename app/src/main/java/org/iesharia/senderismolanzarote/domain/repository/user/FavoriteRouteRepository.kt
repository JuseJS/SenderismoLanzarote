package org.iesharia.senderismolanzarote.domain.repository.user

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.user.FavoriteRouteModel

interface FavoriteRouteRepository {
    fun getUserFavoriteRoutes(userId: Int): Flow<List<FavoriteRouteModel>>
    suspend fun insertFavoriteRoute(favoriteRouteModel: FavoriteRouteModel)
    suspend fun deleteFavoriteRoute(favoriteRouteModel: FavoriteRouteModel)
    suspend fun isRouteFavorite(userId: Int, routeId: Int): Boolean
}