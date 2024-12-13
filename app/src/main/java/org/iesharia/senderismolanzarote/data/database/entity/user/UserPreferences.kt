package org.iesharia.senderismolanzarote.data.database.entity.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "user_preferences",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class UserPreferences(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val preferredDifficulty: Int,
    val maxDistanceKm: BigDecimal,
    val maxDurationMinutes: Int
)