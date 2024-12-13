package org.iesharia.senderismolanzarote.data.database.entity.route.main

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.PoiType
import java.math.BigDecimal

@Entity(
    tableName = "points_of_interest",
    foreignKeys = [
        ForeignKey(
            entity = Route::class,
            parentColumns = ["id"],
            childColumns = ["routeId"]
        ),
        ForeignKey(
            entity = PoiType::class,
            parentColumns = ["id"],
            childColumns = ["typeId"]
        )
    ]
)
data class PointOfInterest(
    @PrimaryKey
    val id: Int,
    val routeId: Int,
    val name: String,
    val typeId: Int,
    val description: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val accessibleReducedMobility: Boolean,
    val estimatedVisitTime: Int
)