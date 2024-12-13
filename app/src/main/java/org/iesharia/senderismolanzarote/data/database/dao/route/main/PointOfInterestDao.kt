package org.iesharia.senderismolanzarote.data.database.dao.route.main

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.main.PointOfInterestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PointOfInterestDao {
    @Query("SELECT * FROM points_of_interest WHERE routeId = :routeId")
    fun getRoutePointsOfInterest(routeId: Int): Flow<List<PointOfInterestEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPointOfInterest(poi: PointOfInterestEntity)

    @Update
    suspend fun updatePointOfInterest(poi: PointOfInterestEntity)

    @Delete
    suspend fun deletePointOfInterest(poi: PointOfInterestEntity)
}