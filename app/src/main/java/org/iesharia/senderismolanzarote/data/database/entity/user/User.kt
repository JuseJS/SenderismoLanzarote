package org.iesharia.senderismolanzarote.data.database.entity.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "users",
    foreignKeys = [
        ForeignKey(
            entity = UserRole::class,
            parentColumns = ["id"],
            childColumns = ["rolId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("rolId")]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val email: String,
    val passwordHash: String,
    val firstName: String,
    val lastName: String,
    val registrationDate: LocalDateTime,
    val rolId: Int,
    val profileImage: String?
)