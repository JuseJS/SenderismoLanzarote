package org.iesharia.senderismolanzarote.domain.model.user

import java.time.LocalDateTime

data class User(
    val id: Int = 0,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val registrationDate: LocalDateTime,
    val roleId: Int,
    val profileImage: String?
)