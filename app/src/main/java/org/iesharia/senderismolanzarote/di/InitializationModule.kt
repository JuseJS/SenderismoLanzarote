package org.iesharia.senderismolanzarote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.iesharia.senderismolanzarote.data.datastore.AppDataStore
import org.iesharia.senderismolanzarote.domain.repository.route.main.*
import org.iesharia.senderismolanzarote.domain.repository.route.reference.*
import org.iesharia.senderismolanzarote.domain.repository.user.*
import org.iesharia.senderismolanzarote.domain.usecase.sample.*
import org.iesharia.senderismolanzarote.domain.usecase.sample.route.main.InsertSampleRouteMainDataUseCase
import org.iesharia.senderismolanzarote.domain.usecase.sample.route.reference.InsertSampleRouteReferenceDataUseCase
import org.iesharia.senderismolanzarote.domain.usecase.sample.user.InsertSampleUserDataUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InitializationModule {

    @Provides
    @Singleton
    fun provideCheckAndInitializeDatabaseUseCase(
        appDataStore: AppDataStore,
        difficultyLevelRepository: DifficultyLevelRepository,
        insertAllSampleDataUseCase: InsertAllSampleDataUseCase
    ): CheckAndInitializeDatabaseUseCase {
        return CheckAndInitializeDatabaseUseCase(
            appDataStore,
            difficultyLevelRepository,
            insertAllSampleDataUseCase
        )
    }

    @Provides
    @Singleton
    fun provideInsertAllSampleDataUseCase(
        insertSampleRouteReferenceDataUseCase: InsertSampleRouteReferenceDataUseCase,
        insertSampleUserDataUseCase: InsertSampleUserDataUseCase,
        insertSampleRouteMainDataUseCase: InsertSampleRouteMainDataUseCase
    ): InsertAllSampleDataUseCase {
        return InsertAllSampleDataUseCase(
            insertSampleRouteReferenceDataUseCase,
            insertSampleUserDataUseCase,
            insertSampleRouteMainDataUseCase
        )
    }

    @Provides
    @Singleton
    fun provideInsertSampleRouteReferenceDataUseCase(
        difficultyLevelRepository: DifficultyLevelRepository,
        seasonRouteRepository: SeasonRouteRepository,
        routeStatusRepository: RouteStatusRepository,
        poiTypeRepository: PoiTypeRepository
    ): InsertSampleRouteReferenceDataUseCase {
        return InsertSampleRouteReferenceDataUseCase(
            difficultyLevelRepository,
            seasonRouteRepository,
            routeStatusRepository,
            poiTypeRepository
        )
    }

    @Provides
    @Singleton
    fun provideInsertSampleUserDataUseCase(
        userRepository: UserRepository,
        userRoleRepository: UserRoleRepository,
        difficultyLevelRepository: DifficultyLevelRepository,
        userPreferencesRepository: UserPreferencesRepository
    ): InsertSampleUserDataUseCase {
        return InsertSampleUserDataUseCase(
            userRepository,
            userRoleRepository,
            difficultyLevelRepository,
            userPreferencesRepository
        )
    }

    @Provides
    @Singleton
    fun provideInsertSampleRouteMainDataUseCase(
        routeRepository: RouteRepository,
        pointOfInterestRepository: PointOfInterestRepository,
        difficultyLevelRepository: DifficultyLevelRepository,
        seasonRouteRepository: SeasonRouteRepository,
        routeStatusRepository: RouteStatusRepository,
        poiTypeRepository: PoiTypeRepository,
    ): InsertSampleRouteMainDataUseCase {
        return InsertSampleRouteMainDataUseCase(
            routeRepository,
            pointOfInterestRepository,
            difficultyLevelRepository,
            seasonRouteRepository,
            routeStatusRepository,
            poiTypeRepository,
        )
    }
}