package org.iesharia.senderismolanzarote.data.database.dao.route.main

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.main.NearbyServiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NearbyServiceDao {
    @Query("SELECT * FROM nearby_services WHERE routeId = :routeId")
    fun getRouteNearbyServices(routeId: Int): Flow<List<NearbyServiceEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNearbyService(service: NearbyServiceEntity)

    @Update
    suspend fun updateNearbyService(service: NearbyServiceEntity)

    @Delete
    suspend fun deleteNearbyService(service: NearbyServiceEntity)
}