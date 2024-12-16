package org.iesharia.senderismolanzarote.domain.model.user

import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel
import java.math.BigDecimal

data class UserPreferencesModel(
    val id: Int = 0,
    val userModel: UserModel,
    val preferredDifficultyLevel: DifficultyLevelModel,
    val maxDistanceKm: BigDecimal = BigDecimal("10.0"),
    val maxDurationMinutes: Int = 120
)