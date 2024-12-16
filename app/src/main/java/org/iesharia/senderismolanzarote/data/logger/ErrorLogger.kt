package org.iesharia.senderismolanzarote.data.logger

import android.util.Log
import org.iesharia.senderismolanzarote.domain.model.error.ErrorModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorLogger @Inject constructor() {
    fun logError(error: ErrorModel) {
        val tag = "SenderismoApp"
        Log.e(tag, "${error.type}: ${error.message}", error.cause)
    }
}