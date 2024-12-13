package org.iesharia.senderismolanzarote.data.database.entity.route.reference

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route_statuses")
data class RouteStatus(
    @PrimaryKey
    val id: Int,
    val status: String,
    val description: String
)