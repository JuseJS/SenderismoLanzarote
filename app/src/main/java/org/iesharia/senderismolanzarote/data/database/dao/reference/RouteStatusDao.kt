package org.iesharia.senderismolanzarote.data.database.dao.reference

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.RouteStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteStatusDao {
    @Query("SELECT * FROM route_statuses")
    fun getAllRouteStatuses(): Flow<List<RouteStatus>>

    @Query("SELECT * FROM route_statuses WHERE id = :id")
    suspend fun getRouteStatusById(id: Int): RouteStatus?
}