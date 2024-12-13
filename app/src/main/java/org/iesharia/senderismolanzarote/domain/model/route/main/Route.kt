package org.iesharia.senderismolanzarote.domain.model.route.main

import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevel
import org.iesharia.senderismolanzarote.domain.model.route.reference.RouteStatus
import org.iesharia.senderismolanzarote.domain.model.route.reference.SeasonRoute
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

data class Route(
    val id: Int,
    val name: String,
    val difficultyLevel: DifficultyLevel,
    val season: SeasonRoute,
    val status: RouteStatus,
    val startLatitude: BigDecimal,
    val startLongitude: BigDecimal,
    val endLatitude: BigDecimal,
    val endLongitude: BigDecimal,
    val distanceKm: BigDecimal,
    val estimatedDuration: LocalTime,
    val elevationGain: Int,
    val description: String,
    val startPoint: String,
    val endPoint: String,
    val lastReviewDate: LocalDate,
    val statusSignage: String,
    val isCircular: Boolean
)