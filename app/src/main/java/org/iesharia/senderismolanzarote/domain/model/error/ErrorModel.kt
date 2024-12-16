package org.iesharia.senderismolanzarote.domain.model.error

data class ErrorModel(
    val type: ErrorType,
    override val message: String,
    override val cause: Throwable? = null
) : Throwable(message, cause)

enum class ErrorType {
    DATABASE,
    NETWORK,
    AUTH,
    VALIDATION,
    UNKNOWN
}