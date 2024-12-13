package org.iesharia.senderismolanzarote.data.database.entity.route.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.DifficultyLevelEntity
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.RouteStatusEntity
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.SeasonRouteEntity
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "routes",
    foreignKeys = [
        ForeignKey(
            entity = DifficultyLevelEntity::class,
            parentColumns = ["id"],
            childColumns = ["difficultyLevelId"]
        ),
        ForeignKey(
            entity = SeasonRouteEntity::class,
            parentColumns = ["id"],
            childColumns = ["seasonId"]
        ),
        ForeignKey(
            entity = RouteStatusEntity::class,
            parentColumns = ["id"],
            childColumns = ["statusId"]
        )
    ],
    indices = [
        Index("difficultyLevelId"),
        Index("seasonId"),
        Index("statusId")
    ]
)
data class RouteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
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
    val lastReviewDate: LocalDate = LocalDate.now(),
    val statusSignage: String,
    val isCircular: Boolean = false
)