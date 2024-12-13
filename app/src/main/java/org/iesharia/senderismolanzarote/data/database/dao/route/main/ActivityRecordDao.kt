package org.iesharia.senderismolanzarote.data.database.dao.route.main

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.main.ActivityRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityRecordDao {
    @Query("SELECT * FROM activity_records WHERE userId = :userId")
    fun getUserActivityRecords(userId: Int): Flow<List<ActivityRecordEntity>>

    @Query("SELECT * FROM activity_records WHERE routeId = :routeId")
    fun getRouteActivityRecords(routeId: Int): Flow<List<ActivityRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertActivityRecord(activityRecordEntity: ActivityRecordEntity)

    @Update
    suspend fun updateActivityRecord(activityRecordEntity: ActivityRecordEntity)

    @Delete
    suspend fun deleteActivityRecord(activityRecordEntity: ActivityRecordEntity)
}