package org.iesharia.senderismolanzarote.data.database.entity.route.main

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.ServiceType

@Entity(
    tableName = "nearby_services",
    foreignKeys = [
        ForeignKey(
            entity = Route::class,
            parentColumns = ["id"],
            childColumns = ["routeId"]
        ),
        ForeignKey(
            entity = ServiceType::class,
            parentColumns = ["id"],
            childColumns = ["serviceTypeId"]
        )
    ]
)
data class NearbyService(
    @PrimaryKey
    val id: Int,
    val routeId: Int,
    val serviceTypeId: Int,
    val name: String,
    val description: String,
    val distanceMeters: Int,
    val businessHours: String,
    val contactInfo: String
)