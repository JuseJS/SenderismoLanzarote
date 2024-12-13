package org.iesharia.senderismolanzarote.data.database.dao.route

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.main.PointOfInterest
import kotlinx.coroutines.flow.Flow

@Dao
interface PointOfInterestDao {
    @Query("SELECT * FROM points_of_interest WHERE routeId = :routeId")
    fun getRoutePointsOfInterest(routeId: Int): Flow<List<PointOfInterest>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPointOfInterest(poi: PointOfInterest)

    @Update
    suspend fun updatePointOfInterest(poi: PointOfInterest)

    @Delete
    suspend fun deletePointOfInterest(poi: PointOfInterest)
}