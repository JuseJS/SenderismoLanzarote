package org.iesharia.senderismolanzarote.data.database.entity.route.reference

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "season_routes")
data class SeasonRoute(
    @PrimaryKey
    val id: Int,
    val season: String,
    val description: String
)