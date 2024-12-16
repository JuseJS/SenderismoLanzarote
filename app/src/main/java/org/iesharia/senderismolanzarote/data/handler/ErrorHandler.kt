package org.iesharia.senderismolanzarote.data.handler

import org.iesharia.senderismolanzarote.data.mapper.error.toErrorModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor() {
    suspend fun <T> handleError(block: suspend () -> T): Result<T> = try {
        Result.success(block())
    } catch (e: Exception) {
        Result.failure(e.toErrorModel())
    }
}