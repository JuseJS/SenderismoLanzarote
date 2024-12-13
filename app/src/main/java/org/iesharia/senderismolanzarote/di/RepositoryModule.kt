package org.iesharia.senderismolanzarote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.iesharia.senderismolanzarote.data.repository.route.main.*
import org.iesharia.senderismolanzarote.data.repository.route.reference.*
import org.iesharia.senderismolanzarote.data.repository.user.*
import org.iesharia.senderismolanzarote.domain.repository.route.main.*
import org.iesharia.senderismolanzarote.domain.repository.route.reference.*
import org.iesharia.senderismolanzarote.domain.repository.user.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindActivityRecordRepository(
        impl: ActivityRecordRepositoryImpl
    ): ActivityRecordRepository

    @Binds
    @Singleton
    abstract fun bindRouteRepository(
        impl: RouteRepositoryImpl
    ): RouteRepository

    @Binds
    @Singleton
    abstract fun bindNearbyServiceRepository(
        impl: NearbyServiceRepositoryImpl
    ): NearbyServiceRepository

    @Binds
    @Singleton
    abstract fun bindPointOfInterestRepository(
        impl: PointOfInterestRepositoryImpl
    ): PointOfInterestRepository

    @Binds
    @Singleton
    abstract fun bindDifficultyLevelRepository(
        impl: DifficultyLevelRepositoryImpl
    ): DifficultyLevelRepository

    @Binds
    @Singleton
    abstract fun bindPoiTypeRepository(
        impl: PoiTypeRepositoryImpl
    ): PoiTypeRepository

    @Binds
    @Singleton
    abstract fun bindRouteStatusRepository(
        impl: RouteStatusRepositoryImpl
    ): RouteStatusRepository

    @Binds
    @Singleton
    abstract fun bindSeasonRouteRepository(
        impl: SeasonRouteRepositoryImpl
    ): SeasonRouteRepository

    @Binds
    @Singleton
    abstract fun bindServiceTypeRepository(
        impl: ServiceTypeRepositoryImpl
    ): ServiceTypeRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRouteRepository(
        impl: FavoriteRouteRepositoryImpl
    ): FavoriteRouteRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindUserRoleRepository(
        impl: UserRoleRepositoryImpl
    ): UserRoleRepository
}