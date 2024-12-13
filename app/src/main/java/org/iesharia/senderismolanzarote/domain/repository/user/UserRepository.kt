package org.iesharia.senderismolanzarote.domain.repository.user

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.user.User

interface UserRepository {
    fun getAllUsers(): Flow<List<User>>
    suspend fun getUserById(userId: Int): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: User): Long
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}