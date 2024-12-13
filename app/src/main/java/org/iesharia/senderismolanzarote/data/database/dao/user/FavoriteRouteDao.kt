package org.iesharia.senderismolanzarote.data.database.dao.user

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.user.FavoriteRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRouteDao {
    @Query("SELECT * FROM favorite_routes WHERE userId = :userId")
    fun getUserFavoriteRoutes(userId: Int): Flow<List<FavoriteRoute>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRoute(favoriteRoute: FavoriteRoute)

    @Delete
    suspend fun deleteFavoriteRoute(favoriteRoute: FavoriteRoute)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_routes WHERE userId = :userId AND routeId = :routeId)")
    suspend fun isRouteFavorite(userId: Int, routeId: Int): Boolean
}