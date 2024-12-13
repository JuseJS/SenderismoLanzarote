package org.iesharia.senderismolanzarote.domain.model.user

import java.math.BigDecimal

data class UserPreferences(
    val id: Int = 0,
    val userId: Int,
    val preferredDifficulty: Int,
    val maxDistanceKm: BigDecimal,
    val maxDurationMinutes: Int
)