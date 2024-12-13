package org.iesharia.senderismolanzarote.domain.repository.user

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.user.UserRole

interface UserRoleRepository {
    fun getAllUserRoles(): Flow<List<UserRole>>
    suspend fun getUserRoleById(roleId: Int): UserRole?
    suspend fun insertUserRole(role: UserRole)
    suspend fun updateUserRole(role: UserRole)
    suspend fun deleteUserRole(role: UserRole)
}