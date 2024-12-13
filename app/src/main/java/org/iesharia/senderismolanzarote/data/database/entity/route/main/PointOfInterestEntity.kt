package org.iesharia.senderismolanzarote.data.database.entity.route.main

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.PoiTypeEntity
import java.math.BigDecimal

@Entity(
    tableName = "points_of_interest",
    foreignKeys = [
        ForeignKey(
            entity = RouteEntity::class,
            parentColumns = ["id"],
            childColumns = ["routeId"]
        ),
        ForeignKey(
            entity = PoiTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["typeId"]
        )
    ],
    indices = [
        Index("routeId"),
        Index("typeId")
    ]
)
data class PointOfInterestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val routeId: Int,
    val name: String,
    val typeId: Int,
    val description: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val accessibleReducedMobility: Boolean = false,
    val estimatedVisitTime: Int = 0
)