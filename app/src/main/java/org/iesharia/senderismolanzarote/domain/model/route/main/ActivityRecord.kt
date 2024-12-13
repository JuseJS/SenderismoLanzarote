package org.iesharia.senderismolanzarote.domain.model.route.main

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

data class ActivityRecord(
    val id: Int,
    val userId: Int,
    val route: Route,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val totalTime: LocalTime,
    val actualDistanceKm: BigDecimal,
    val comments: String
)