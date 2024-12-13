package org.iesharia.senderismolanzarote.data.repository.route.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.main.ActivityRecordDao
import org.iesharia.senderismolanzarote.data.database.dao.route.main.RouteDao
import org.iesharia.senderismolanzarote.data.database.dao.user.UserDao
import org.iesharia.senderismolanzarote.data.mapper.route.main.toActivityRecord
import org.iesharia.senderismolanzarote.data.mapper.route.main.toActivityRecordEntity
import org.iesharia.senderismolanzarote.data.mapper.route.main.toRoute
import org.iesharia.senderismolanzarote.data.mapper.user.toUser
import org.iesharia.senderismolanzarote.domain.model.route.main.ActivityRecordModel
import org.iesharia.senderismolanzarote.domain.repository.route.main.ActivityRecordRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.DifficultyLevelRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.RouteStatusRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.SeasonRouteRepository
import javax.inject.Inject

class ActivityRecordRepositoryImpl @Inject constructor(
    private val activityRecordDao: ActivityRecordDao,
    private val userDao: UserDao,
    private val routeDao: RouteDao,
    private val difficultyLevelRepository: DifficultyLevelRepository,
    private val seasonRouteRepository: SeasonRouteRepository,
    private val routeStatusRepository: RouteStatusRepository
) : ActivityRecordRepository {

    override fun getUserActivityRecords(userId: Int): Flow<List<ActivityRecordModel>> {
        return activityRecordDao.getUserActivityRecords(userId).map { entities ->
            entities.mapNotNull { entity ->
                val user = userDao.getUserById(entity.userId)?.toUser()
                val route = routeDao.getRouteById(entity.routeId)?.let { routeEntity ->
                    val difficulty = difficultyLevelRepository.getDifficultyLevelById(routeEntity.difficultyLevelId)
                    val season = seasonRouteRepository.getSeasonRouteById(routeEntity.seasonId)
                    val status = routeStatusRepository.getRouteStatusById(routeEntity.statusId)

                    if (difficulty != null && season != null && status != null) {
                        routeEntity.toRoute(difficulty, season, status)
                    } else null
                }

                if (user != null && route != null) {
                    entity.toActivityRecord(user, route)
                } else null
            }
        }
    }

    override fun getRouteActivityRecords(routeId: Int): Flow<List<ActivityRecordModel>> {
        return activityRecordDao.getRouteActivityRecords(routeId).map { entities ->
            entities.mapNotNull { entity ->
                val user = userDao.getUserById(entity.userId)?.toUser()
                val route = routeDao.getRouteById(entity.routeId)?.let { routeEntity ->
                    val difficulty = difficultyLevelRepository.getDifficultyLevelById(routeEntity.difficultyLevelId)
                    val season = seasonRouteRepository.getSeasonRouteById(routeEntity.seasonId)
                    val status = routeStatusRepository.getRouteStatusById(routeEntity.statusId)

                    if (difficulty != null && season != null && status != null) {
                        routeEntity.toRoute(difficulty, season, status)
                    } else null
                }

                if (user != null && route != null) {
                    entity.toActivityRecord(user, route)
                } else null
            }
        }
    }

    override suspend fun insertActivityRecord(activityRecordModel: ActivityRecordModel) {
        activityRecordDao.insertActivityRecord(activityRecordModel.toActivityRecordEntity())
    }

    override suspend fun updateActivityRecord(activityRecordModel: ActivityRecordModel) {
        activityRecordDao.updateActivityRecord(activityRecordModel.toActivityRecordEntity())
    }

    override suspend fun deleteActivityRecord(activityRecordModel: ActivityRecordModel) {
        activityRecordDao.deleteActivityRecord(activityRecordModel.toActivityRecordEntity())
    }
}