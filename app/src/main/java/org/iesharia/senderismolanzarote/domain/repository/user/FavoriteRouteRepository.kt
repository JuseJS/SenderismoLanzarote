package org.iesharia.senderismolanzarote.domain.repository.user

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.user.FavoriteRouteModel

interface FavoriteRouteRepository {
    fun observeFavoriteRoutes(userId: Int): Flow<List<FavoriteRouteModel>>
    suspend fun isRouteFavorite(userId: Int, routeId: Int): Boolean
    suspend fun insertFavoriteRoute(favoriteRoute: FavoriteRouteModel)
    suspend fun deleteFavoriteRoute(favoriteRoute: FavoriteRouteModel)
    suspend fun getUserFavoriteRoutes(userId: Int): Flow<List<FavoriteRouteModel>>
}