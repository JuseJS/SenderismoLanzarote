package org.iesharia.senderismolanzarote.data.database.entity.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.time.LocalDateTime

@Entity(
    tableName = "favorite_routes",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")],
    primaryKeys = ["userId", "routeId"]
)
data class FavoriteRoute(
    val userId: Int,
    val routeId: Int,
    val addedDate: LocalDateTime
)