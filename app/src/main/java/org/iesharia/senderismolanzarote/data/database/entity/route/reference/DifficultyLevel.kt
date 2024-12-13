package org.iesharia.senderismolanzarote.data.database.entity.route.reference

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "difficulty_levels")
data class DifficultyLevel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String
)