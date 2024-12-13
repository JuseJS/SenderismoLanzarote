package org.iesharia.senderismolanzarote.data.database.entity.route.main

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.ServiceTypeEntity

@Entity(
    tableName = "nearby_services",
    foreignKeys = [
        ForeignKey(
            entity = RouteEntity::class,
            parentColumns = ["id"],
            childColumns = ["routeId"]
        ),
        ForeignKey(
            entity = ServiceTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["serviceTypeId"]
        )
    ],
    indices = [
        Index("routeId"),
        Index("serviceTypeId")
    ]
)
data class NearbyServiceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val routeId: Int,
    val serviceTypeId: Int,
    val name: String,
    val description: String,
    val distanceMeters: Int,
    val businessHours: String = "",
    val contactInfo: String = ""
)