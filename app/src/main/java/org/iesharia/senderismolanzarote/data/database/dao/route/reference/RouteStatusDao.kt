package org.iesharia.senderismolanzarote.data.database.dao.route.reference

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.RouteStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteStatusDao {
    @Query("SELECT * FROM route_statuses")
    fun getAllRouteStatuses(): Flow<List<RouteStatusEntity>>

    @Query("SELECT * FROM route_statuses WHERE id = :id")
    suspend fun getRouteStatusById(id: Int): RouteStatusEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRouteStatus(routeStatus: RouteStatusEntity)
}