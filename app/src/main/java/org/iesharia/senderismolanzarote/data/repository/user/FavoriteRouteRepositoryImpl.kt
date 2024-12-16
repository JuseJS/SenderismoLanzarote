package org.iesharia.senderismolanzarote.data.repository.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.user.FavoriteRouteDao
import org.iesharia.senderismolanzarote.data.mapper.user.toFavoriteRoute
import org.iesharia.senderismolanzarote.data.mapper.user.toFavoriteRouteEntity
import org.iesharia.senderismolanzarote.domain.model.user.FavoriteRouteModel
import org.iesharia.senderismolanzarote.domain.repository.user.FavoriteRouteRepository
import org.iesharia.senderismolanzarote.domain.repository.route.main.RouteRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRouteRepositoryImpl @Inject constructor(
    private val favoriteRouteDao: FavoriteRouteDao,
    private val userRepository: UserRepository,
    private val routeRepository: RouteRepository
) : FavoriteRouteRepository {

    override fun getUserFavoriteRoutes(userId: Int): Flow<List<FavoriteRouteModel>> {
        return favoriteRouteDao.getUserFavoriteRoutes(userId).map { entities ->
            entities.mapNotNull { entity ->
                val user = userRepository.getUserById(entity.userId)
                val route = routeRepository.getRouteById(entity.routeId)

                if (user != null && route != null) {
                    entity.toFavoriteRoute(user, route)
                } else null
            }
        }
    }

    override suspend fun insertFavoriteRoute(favoriteRouteModel: FavoriteRouteModel) {
        favoriteRouteDao.insertFavoriteRoute(favoriteRouteModel.toFavoriteRouteEntity())
    }

    override suspend fun deleteFavoriteRoute(favoriteRouteModel: FavoriteRouteModel) {
        favoriteRouteDao.deleteFavoriteRoute(favoriteRouteModel.toFavoriteRouteEntity())
    }

    override suspend fun isRouteFavorite(userId: Int, routeId: Int): Boolean {
        return favoriteRouteDao.isRouteFavorite(userId, routeId)
    }
}