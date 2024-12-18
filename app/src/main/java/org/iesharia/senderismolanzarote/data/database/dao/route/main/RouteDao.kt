package org.iesharia.senderismolanzarote.data.database.dao.route.main

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.main.RouteEntity
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface RouteDao {
    @Query("SELECT * FROM routes")
    fun getAllRoutes(): Flow<List<RouteEntity>>

    @Query("SELECT * FROM routes WHERE id = :routeId")
    suspend fun getRouteById(routeId: Int): RouteEntity?

    @Query("""
        SELECT * FROM routes 
        WHERE difficultyLevelId <= :maxDifficulty 
        AND CAST(distanceKm AS DECIMAL) <= :maxDistance
        AND CAST(
            (substr(estimatedDuration, 1, instr(estimatedDuration, ':') - 1) * 60) + 
            substr(estimatedDuration, instr(estimatedDuration, ':') + 1)
            AS INTEGER) <= :maxDurationMinutes
    """)
    fun getRoutesByPreferences(
        maxDifficulty: Int,
        maxDistance: BigDecimal,
        maxDurationMinutes: Int
    ): Flow<List<RouteEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRoute(routeEntity: RouteEntity)

    @Update
    suspend fun updateRoute(routeEntity: RouteEntity)

    @Delete
    suspend fun deleteRoute(routeEntity: RouteEntity)
}