package org.iesharia.senderismolanzarote.domain.model.route.main

import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel
import org.iesharia.senderismolanzarote.domain.model.route.reference.RouteStatusModel
import org.iesharia.senderismolanzarote.domain.model.route.reference.SeasonRouteModel
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

data class RouteModel(
    val id: Int = 0,
    val name: String,
    val difficultyLevelModel: DifficultyLevelModel,
    val season: SeasonRouteModel,
    val status: RouteStatusModel,
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
    val lastReviewDate: LocalDate = LocalDate.now(),
    val statusSignage: String,
    val isCircular: Boolean = false
)