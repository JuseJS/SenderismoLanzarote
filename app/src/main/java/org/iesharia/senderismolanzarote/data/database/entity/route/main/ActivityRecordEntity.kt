package org.iesharia.senderismolanzarote.data.database.entity.route.main

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.iesharia.senderismolanzarote.data.database.entity.user.UserEntity
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(
    tableName = "activity_records",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RouteEntity::class,
            parentColumns = ["id"],
            childColumns = ["routeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId"),
        Index("routeId")
    ]
)
data class ActivityRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val routeId: Int,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val totalTime: LocalTime = LocalTime.of(0, 0),
    val actualDistanceKm: BigDecimal,
    val comments: String = ""
)