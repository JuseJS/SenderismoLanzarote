package org.iesharia.senderismolanzarote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.iesharia.senderismolanzarote.data.handler.ErrorHandler
import org.iesharia.senderismolanzarote.data.logger.ErrorLogger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ErrorModule {
    @Provides
    @Singleton
    fun provideErrorHandler(): ErrorHandler = ErrorHandler()

    @Provides
    @Singleton
    fun provideErrorLogger(): ErrorLogger = ErrorLogger()
}