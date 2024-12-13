package org.iesharia.senderismolanzarote.data.database.entity.route.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.DifficultyLevel
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.RouteStatus
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.SeasonRoute
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "routes",
    foreignKeys = [
        ForeignKey(
            entity = DifficultyLevel::class,
            parentColumns = ["id"],
            childColumns = ["difficultyLevelId"]
        ),
        ForeignKey(
            entity = SeasonRoute::class,
            parentColumns = ["id"],
            childColumns = ["seasonId"]
        ),
        ForeignKey(
            entity = RouteStatus::class,
            parentColumns = ["id"],
            childColumns = ["statusId"]
        )
    ]
)
data class Route(
    @PrimaryKey
    val id: Int,
    val name: String,
    val difficultyLevelId: Int,
    val seasonId: Int,
    val statusId: Int,
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