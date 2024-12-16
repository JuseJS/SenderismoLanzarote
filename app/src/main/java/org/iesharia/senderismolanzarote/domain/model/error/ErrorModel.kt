package org.iesharia.senderismolanzarote.domain.model.error

sealed class AppError : Exception() {
    data class Database(override val message: String, override val cause: Throwable? = null) : AppError()
    data class Network(override val message: String, override val cause: Throwable? = null) : AppError()
    data class Auth(override val message: String, override val cause: Throwable? = null) : AppError()
    data class Validation(override val message: String, override val cause: Throwable? = null) : AppError()
    data class Unknown(override val message: String, override val cause: Throwable? = null) : AppError()
}