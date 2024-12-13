package org.iesharia.senderismolanzarote.domain.repository.route.main

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.main.ActivityRecordModel

interface ActivityRecordRepository {
    fun getUserActivityRecords(userId: Int): Flow<List<ActivityRecordModel>>
    fun getRouteActivityRecords(routeId: Int): Flow<List<ActivityRecordModel>>
    suspend fun insertActivityRecord(activityRecordModel: ActivityRecordModel)
    suspend fun updateActivityRecord(activityRecordModel: ActivityRecordModel)
    suspend fun deleteActivityRecord(activityRecordModel: ActivityRecordModel)
}