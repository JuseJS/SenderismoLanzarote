package org.iesharia.senderismolanzarote.data.database.dao.route.reference

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.SeasonRouteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeasonRouteDao {
    @Query("SELECT * FROM season_routes")
    fun getAllSeasonRoutes(): Flow<List<SeasonRouteEntity>>

    @Query("SELECT * FROM season_routes WHERE id = :id")
    suspend fun getSeasonRouteById(id: Int): SeasonRouteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonRoute(seasonRoute: SeasonRouteEntity)
}