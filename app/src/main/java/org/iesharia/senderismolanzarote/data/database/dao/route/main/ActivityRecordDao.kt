package org.iesharia.senderismolanzarote.data.database.dao.route.main

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.main.ActivityRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityRecordDao {
    @Query("SELECT * FROM activity_records WHERE userId = :userId")
    fun getUserActivityRecords(userId: Int): Flow<List<ActivityRecord>>

    @Query("SELECT * FROM activity_records WHERE routeId = :routeId")
    fun getRouteActivityRecords(routeId: Int): Flow<List<ActivityRecord>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertActivityRecord(activityRecord: ActivityRecord)

    @Update
    suspend fun updateActivityRecord(activityRecord: ActivityRecord)

    @Delete
    suspend fun deleteActivityRecord(activityRecord: ActivityRecord)
}