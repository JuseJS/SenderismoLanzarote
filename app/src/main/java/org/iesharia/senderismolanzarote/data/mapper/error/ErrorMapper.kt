package org.iesharia.senderismolanzarote.data.mapper.error

import android.database.sqlite.SQLiteException
import java.net.UnknownHostException
import org.iesharia.senderismolanzarote.domain.model.error.ErrorModel
import org.iesharia.senderismolanzarote.domain.model.error.ErrorType

fun Throwable.toErrorModel(): ErrorModel = when (this) {
    is SQLiteException -> ErrorModel(
        type = ErrorType.DATABASE,
        message = "Error en la base de datos: $message",
        cause = this
    )
    is UnknownHostException -> ErrorModel(
        type = ErrorType.NETWORK,
        message = "Error de conexión: $message",
        cause = this
    )
    else -> ErrorModel(
        type = ErrorType.UNKNOWN,
        message = message ?: "Error desconocido",
        cause = this
    )
}