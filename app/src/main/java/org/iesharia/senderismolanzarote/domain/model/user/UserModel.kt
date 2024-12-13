package org.iesharia.senderismolanzarote.domain.model.user

import java.time.LocalDateTime

data class UserModel(
    val id: Int = 0,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val registrationDate: LocalDateTime = LocalDateTime.now(),
    val roleId: Int,
    val profileImage: String? = null
)