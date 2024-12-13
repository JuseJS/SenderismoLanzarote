package org.iesharia.senderismolanzarote.domain.model.route.main

import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

data class ActivityRecordModel(
    val id: Int = 0,
    val userModel: UserModel,
    val routeModel: RouteModel,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val totalTime: LocalTime = LocalTime.of(0, 0),
    val actualDistanceKm: BigDecimal,
    val comments: String = ""
)