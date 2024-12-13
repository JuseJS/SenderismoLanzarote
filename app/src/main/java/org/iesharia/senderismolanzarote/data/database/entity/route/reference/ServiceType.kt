package org.iesharia.senderismolanzarote.data.database.entity.route.reference

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_types")
data class ServiceType(
    @PrimaryKey
    val id: Int,
    val type: String,
    val description: String
)