package org.iesharia.senderismolanzarote.domain.repository.route.main

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.main.ActivityRecord

interface ActivityRecordRepository {
    fun getUserActivityRecords(userId: Int): Flow<List<ActivityRecord>>
    fun getRouteActivityRecords(routeId: Int): Flow<List<ActivityRecord>>
    suspend fun insertActivityRecord(activityRecord: ActivityRecord)
    suspend fun updateActivityRecord(activityRecord: ActivityRecord)
    suspend fun deleteActivityRecord(activityRecord: ActivityRecord)
}