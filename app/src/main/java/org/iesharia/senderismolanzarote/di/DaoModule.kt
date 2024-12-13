package org.iesharia.senderismolanzarote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.iesharia.senderismolanzarote.data.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideActivityRecordDao(db: AppDatabase) = db.activityRecordDao()

    @Provides
    @Singleton
    fun provideRouteDao(db: AppDatabase) = db.routeDao()

    @Provides
    @Singleton
    fun provideNearbyServiceDao(db: AppDatabase) = db.nearbyServiceDao()

    @Provides
    @Singleton
    fun providePointOfInterestDao(db: AppDatabase) = db.pointOfInterestDao()

    @Provides
    @Singleton
    fun provideDifficultyLevelDao(db: AppDatabase) = db.difficultyLevelDao()

    @Provides
    @Singleton
    fun providePoiTypeDao(db: AppDatabase) = db.poiTypeDao()

    @Provides
    @Singleton
    fun provideRouteStatusDao(db: AppDatabase) = db.routeStatusDao()

    @Provides
    @Singleton
    fun provideSeasonRouteDao(db: AppDatabase) = db.seasonRouteDao()

    @Provides
    @Singleton
    fun provideServiceTypeDao(db: AppDatabase) = db.serviceTypeDao()

    @Provides
    @Singleton
    fun provideFavoriteRouteDao(db: AppDatabase) = db.favoriteRouteDao()

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Provides
    @Singleton
    fun provideUserPreferencesDao(db: AppDatabase) = db.userPreferencesDao()

    @Provides
    @Singleton
    fun provideUserRoleDao(db: AppDatabase) = db.userRoleDao()
}