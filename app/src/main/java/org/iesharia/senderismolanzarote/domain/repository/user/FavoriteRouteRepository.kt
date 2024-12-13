package org.iesharia.senderismolanzarote.domain.repository.user

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.user.FavoriteRoute

interface FavoriteRouteRepository {
    fun getUserFavoriteRoutes(userId: Int): Flow<List<FavoriteRoute>>
    suspend fun insertFavoriteRoute(favoriteRoute: FavoriteRoute)
    suspend fun deleteFavoriteRoute(favoriteRoute: FavoriteRoute)
    suspend fun isRouteFavorite(userId: Int, routeId: Int): Boolean
}