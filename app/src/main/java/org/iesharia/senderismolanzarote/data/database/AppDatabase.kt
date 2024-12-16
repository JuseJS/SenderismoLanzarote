package org.iesharia.senderismolanzarote.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.iesharia.senderismolanzarote.data.database.converter.Converters
import org.iesharia.senderismolanzarote.data.database.dao.route.main.*
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.*
import org.iesharia.senderismolanzarote.data.database.dao.user.*
import org.iesharia.senderismolanzarote.data.database.entity.route.main.*
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.*
import org.iesharia.senderismolanzarote.data.database.entity.user.*

@Database(
    entities = [
        // Entidades principales de rutas
        RouteEntity::class,
        ActivityRecordEntity::class,
        PointOfInterestEntity::class,

        // Entidades de referencia de rutas
        DifficultyLevelEntity::class,
        PoiTypeEntity::class,
        RouteStatusEntity::class,
        SeasonRouteEntity::class,

        // Entidades de usuario
        UserEntity::class,
        UserRoleEntity::class,
        UserPreferencesEntity::class,
        FavoriteRouteEntity::class
    ],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    // DAOs principales de rutas
    abstract val routeDao: RouteDao
    abstract val activityRecordDao: ActivityRecordDao
    abstract val pointOfInterestDao: PointOfInterestDao

    // DAOs de referencia de rutas
    abstract val difficultyLevelDao: DifficultyLevelDao
    abstract val poiTypeDao: PoiTypeDao
    abstract val routeStatusDao: RouteStatusDao
    abstract val seasonRouteDao: SeasonRouteDao

    // DAOs de usuario
    abstract val userDao: UserDao
    abstract val userRoleDao: UserRoleDao
    abstract val userPreferencesDao: UserPreferencesDao
    abstract val favoriteRouteDao: FavoriteRouteDao

    companion object {
        const val DATABASE_NAME = "senderismo_lanzarote.db"
    }
}