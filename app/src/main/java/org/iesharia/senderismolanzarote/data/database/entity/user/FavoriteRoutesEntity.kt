package org.iesharia.senderismolanzarote.data.database.entity.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import org.iesharia.senderismolanzarote.data.database.entity.route.main.RouteEntity
import java.time.LocalDateTime

@Entity(
    tableName = "favorite_routes",
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
    ],
    primaryKeys = ["userId", "routeId"]
)
data class FavoriteRouteEntity(
    val userId: Int,
    val routeId: Int,
    val addedDate: LocalDateTime = LocalDateTime.now()
)