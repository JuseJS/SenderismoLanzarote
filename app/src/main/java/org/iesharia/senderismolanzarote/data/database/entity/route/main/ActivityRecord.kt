package org.iesharia.senderismolanzarote.data.database.entity.route.main

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.iesharia.senderismolanzarote.data.database.entity.user.User
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(
    tableName = "activity_records",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Route::class,
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
data class ActivityRecord(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val routeId: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val totalTime: LocalTime,
    val actualDistanceKm: BigDecimal,
    val comments: String
)
