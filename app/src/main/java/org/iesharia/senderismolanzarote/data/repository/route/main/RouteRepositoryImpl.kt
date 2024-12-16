package org.iesharia.senderismolanzarote.data.repository.route.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.main.RouteDao
import org.iesharia.senderismolanzarote.data.handler.ErrorHandler
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
    private val routeStatusRepository: RouteStatusRepository,
    private val errorHandler: ErrorHandler
) : RouteRepository {

    override fun getAllRoutes(): Flow<List<RouteModel>> {
        return routeDao.getAllRoutes().map { entities ->
            entities.mapNotNull { entity ->
                errorHandler.handleError {
                    val difficulty = difficultyLevelRepository.getDifficultyLevelById(entity.difficultyLevelId)
                    val season = seasonRouteRepository.getSeasonRouteById(entity.seasonId)
                    val status = routeStatusRepository.getRouteStatusById(entity.statusId)

                    if (difficulty != null && season != null && status != null) {
                        entity.toRoute(difficulty, season, status)
                    } else null
                }.getOrNull()
            }
        }
    }

    override suspend fun getRouteById(routeId: Int): RouteModel? {
        return errorHandler.handleError {
            val entity = routeDao.getRouteById(routeId)
            if (entity != null) {
                val difficulty = difficultyLevelRepository.getDifficultyLevelById(entity.difficultyLevelId)
                val season = seasonRouteRepository.getSeasonRouteById(entity.seasonId)
                val status = routeStatusRepository.getRouteStatusById(entity.statusId)

                if (difficulty != null && season != null && status != null) {
                    entity.toRoute(difficulty, season, status)
                } else null
            } else null
        }.getOrNull()
    }

    override fun getRoutesByPreferences(
        maxDifficulty: Int,
        maxDistance: BigDecimal,
        maxDurationMinutes: Int
    ): Flow<List<RouteModel>> {
        return routeDao.getRoutesByPreferences(maxDifficulty, maxDistance, maxDurationMinutes)
            .map { entities ->
                entities.mapNotNull { entity ->
                    errorHandler.handleError {
                        val difficulty = difficultyLevelRepository.getDifficultyLevelById(entity.difficultyLevelId)
                        val season = seasonRouteRepository.getSeasonRouteById(entity.seasonId)
                        val status = routeStatusRepository.getRouteStatusById(entity.statusId)

                        if (difficulty != null && season != null && status != null) {
                            entity.toRoute(difficulty, season, status)
                        } else null
                    }.getOrNull()
                }
            }
    }

    override suspend fun insertRoute(routeModel: RouteModel) {
        errorHandler.handleError {
            routeDao.insertRoute(routeModel.toRouteEntity())
        }
    }

    override suspend fun updateRoute(routeModel: RouteModel) {
        errorHandler.handleError {
            routeDao.updateRoute(routeModel.toRouteEntity())
        }
    }

    override suspend fun deleteRoute(routeModel: RouteModel) {
        errorHandler.handleError {
            routeDao.deleteRoute(routeModel.toRouteEntity())
        }
    }
}
