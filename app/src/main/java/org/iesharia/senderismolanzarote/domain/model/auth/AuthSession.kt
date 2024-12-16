package org.iesharia.senderismolanzarote.domain.model.auth

data class AuthSession(
    val userId: Int,
    val token: String
)