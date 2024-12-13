package org.iesharia.senderismolanzarote.data.database.entity.route.reference

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "season_routes")
data class SeasonRouteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val season: String,
    val description: String
)