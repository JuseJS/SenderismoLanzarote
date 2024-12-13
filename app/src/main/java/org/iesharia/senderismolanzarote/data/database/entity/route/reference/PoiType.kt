package org.iesharia.senderismolanzarote.data.database.entity.route.reference

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poi_types")
data class PoiType(
    @PrimaryKey
    val id: Int,
    val type: String,
    val description: String
)
