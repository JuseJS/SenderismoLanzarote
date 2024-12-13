package org.iesharia.senderismolanzarote.domain.repository.user

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.user.UserModel

interface UserRepository {
    fun getAllUsers(): Flow<List<UserModel>>
    suspend fun getUserById(userId: Int): UserModel?
    suspend fun getUserByEmail(email: String): UserModel?
    suspend fun insertUser(user: UserModel): Long
    suspend fun updateUser(user: UserModel)
    suspend fun deleteUser(user: UserModel)
}