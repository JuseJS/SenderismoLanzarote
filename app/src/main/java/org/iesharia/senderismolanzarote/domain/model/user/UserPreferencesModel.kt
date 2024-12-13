package org.iesharia.senderismolanzarote.domain.model.user

import java.math.BigDecimal

data class UserPreferencesModel(
    val id: Int = 0,
    val userModel: UserModel,
    val preferredDifficulty: Int = 1,
    val maxDistanceKm: BigDecimal = BigDecimal("10.0"),
    val maxDurationMinutes: Int = 120
)