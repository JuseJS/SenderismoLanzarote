package org.iesharia.senderismolanzarote.data.database.dao.route.main

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.main.NearbyService
import kotlinx.coroutines.flow.Flow

@Dao
interface NearbyServiceDao {
    @Query("SELECT * FROM nearby_services WHERE routeId = :routeId")
    fun getRouteNearbyServices(routeId: Int): Flow<List<NearbyService>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNearbyService(service: NearbyService)

    @Update
    suspend fun updateNearbyService(service: NearbyService)

    @Delete
    suspend fun deleteNearbyService(service: NearbyService)
}