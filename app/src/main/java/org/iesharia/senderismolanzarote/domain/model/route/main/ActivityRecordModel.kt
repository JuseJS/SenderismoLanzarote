package org.iesharia.senderismolanzarote.domain.model.route.main

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

data class ActivityRecord(
    val id: Int = 0,
    val userId: Int,
    val route: Route,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val totalTime: LocalTime = LocalTime.of(0, 0),
    val actualDistanceKm: BigDecimal,
    val comments: String = ""
)