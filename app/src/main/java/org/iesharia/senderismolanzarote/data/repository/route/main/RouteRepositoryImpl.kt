package org.iesharia.senderismolanzarote.data.repository.route.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.main.RouteDao
import org.iesharia.senderismolanzarote.data.mapper.route.main.toRoute
import org.iesharia.senderismolanzarote.data.mapper.route.main.toRouteEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import org.iesharia.senderismolanzarote.domain.repository.route.main.RouteRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.DifficultyLevelRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.RouteStatusRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.SeasonRouteRepository
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteRepositoryImpl @Inject constructor(
    private val routeDao: RouteDao,
    private val difficultyLevelRepository: DifficultyLevelRepository,
    private val seasonRouteRepository: SeasonRouteRepository,
    private val routeStatusRepository: RouteStatusRepository
) : RouteRepository {

    override fun getAllRoutes(): Flow<List<RouteModel>> {
        return routeDao.getAllRoutes().map { entities ->
            entities.mapNotNull { entity ->
                val difficulty = difficultyLevelRepository.getDifficultyLevelById(entity.difficultyLevelId)
                val season = seasonRouteRepository.getSeasonRouteById(entity.seasonId)
                val status = routeStatusRepository.getRouteStatusById(entity.statusId)

                if (difficulty != null && season != null && status != null) {
                    entity.toRoute(difficulty, season, status)
                } else null
            }
        }
    }

    override suspend fun getRouteById(routeId: Int): RouteModel? {
        val entity = routeDao.getRouteById(routeId)
        if (entity != null) {
            val difficulty = difficultyLevelRepository.getDifficultyLevelById(entity.difficultyLevelId)
            val season = seasonRouteRepository.getSeasonRouteById(entity.seasonId)
            val status = routeStatusRepository.getRouteStatusById(entity.statusId)

            if (difficulty != null && season != null && status != null) {
                return entity.toRoute(difficulty, season, status)
            }
        }
        return null
    }

    override fun getRoutesByPreferences(
        maxDifficulty: Int,
        maxDistance: BigDecimal,
        maxDurationMinutes: Int
    ): Flow<List<RouteModel>> {
        return routeDao.getRoutesByPreferences(maxDifficulty, maxDistance, maxDurationMinutes)
            .map { entities ->
                entities.mapNotNull { entity ->
                    val difficulty = difficultyLevelRepository.getDifficultyLevelById(entity.difficultyLevelId)
                    val season = seasonRouteRepository.getSeasonRouteById(entity.seasonId)
                    val status = routeStatusRepository.getRouteStatusById(entity.statusId)

                    if (difficulty != null && season != null && status != null) {
                        entity.toRoute(difficulty, season, status)
                    } else null
                }
            }
    }

    override suspend fun insertRoute(routeModel: RouteModel) {
        routeDao.insertRoute(routeModel.toRouteEntity())
    }

    override suspend fun updateRoute(routeModel: RouteModel) {
        routeDao.updateRoute(routeModel.toRouteEntity())
    }

    override suspend fun deleteRoute(routeModel: RouteModel) {
        routeDao.deleteRoute(routeModel.toRouteEntity())
    }
}