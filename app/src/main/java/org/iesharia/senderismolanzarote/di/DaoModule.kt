package org.iesharia.senderismolanzarote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.iesharia.senderismolanzarote.data.database.AppDatabase
import org.iesharia.senderismolanzarote.data.database.dao.route.main.ActivityRecordDao
import org.iesharia.senderismolanzarote.data.database.dao.route.main.PointOfInterestDao
import org.iesharia.senderismolanzarote.data.database.dao.route.main.RouteDao
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.DifficultyLevelDao
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.PoiTypeDao
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.RouteStatusDao
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.SeasonRouteDao
import org.iesharia.senderismolanzarote.data.database.dao.user.FavoriteRouteDao
import org.iesharia.senderismolanzarote.data.database.dao.user.UserDao
import org.iesharia.senderismolanzarote.data.database.dao.user.UserPreferencesDao
import org.iesharia.senderismolanzarote.data.database.dao.user.UserRoleDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideActivityRecordDao(appDatabase: AppDatabase): ActivityRecordDao {
        return appDatabase.activityRecordDao
    }

    @Provides
    @Singleton
    fun provideRouteDao(appDatabase: AppDatabase): RouteDao {
        return appDatabase.routeDao
    }

    @Provides
    @Singleton
    fun providePointOfInterestDao(appDatabase: AppDatabase): PointOfInterestDao {
        return appDatabase.pointOfInterestDao
    }

    @Provides
    @Singleton
    fun provideDifficultyLevelDao(appDatabase: AppDatabase): DifficultyLevelDao {
        return appDatabase.difficultyLevelDao
    }

    @Provides
    @Singleton
    fun providePoiTypeDao(appDatabase: AppDatabase): PoiTypeDao {
        return appDatabase.poiTypeDao
    }

    @Provides
    @Singleton
    fun provideRouteStatusDao(appDatabase: AppDatabase): RouteStatusDao {
        return appDatabase.routeStatusDao
    }

    @Provides
    @Singleton
    fun provideSeasonRouteDao(appDatabase: AppDatabase): SeasonRouteDao {
        return appDatabase.seasonRouteDao
    }

    @Provides
    @Singleton
    fun provideFavoriteRouteDao(appDatabase: AppDatabase): FavoriteRouteDao {
        return appDatabase.favoriteRouteDao
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDao(appDatabase: AppDatabase): UserPreferencesDao {
        return appDatabase.userPreferencesDao
    }

    @Provides
    @Singleton
    fun provideUserRoleDao(appDatabase: AppDatabase): UserRoleDao {
        return appDatabase.userRoleDao
    }
}