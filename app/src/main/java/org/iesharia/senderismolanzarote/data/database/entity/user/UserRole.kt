package org.iesharia.senderismolanzarote.data.database.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_roles")
data class UserRole(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String
)