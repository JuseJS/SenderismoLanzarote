package org.iesharia.senderismolanzarote.data.database.dao.route

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.main.Route
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface RouteDao {
    @Query("SELECT * FROM routes")
    fun getAllRoutes(): Flow<List<Route>>

    @Query("SELECT * FROM routes WHERE id = :routeId")
    suspend fun getRouteById(routeId: Int): Route?

    @Query("""
        SELECT * FROM routes 
        WHERE difficultyLevelId <= :maxDifficulty 
        AND distanceKm <= :maxDistance
        AND CAST((substr(estimatedDuration, 1, 2) * 60 + substr(estimatedDuration, 4, 2)) AS INTEGER) <= :maxDurationMinutes
    """)
    fun getRoutesByPreferences(
        maxDifficulty: Int,
        maxDistance: BigDecimal,
        maxDurationMinutes: Int
    ): Flow<List<Route>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRoute(route: Route)

    @Update
    suspend fun updateRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)
}