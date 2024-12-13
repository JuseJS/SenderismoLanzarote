package org.iesharia.senderismolanzarote.domain.repository.user

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.user.UserRoleModel

interface UserRoleRepository {
    fun getAllUserRoles(): Flow<List<UserRoleModel>>
    suspend fun getUserRoleById(roleId: Int): UserRoleModel?
    suspend fun insertUserRole(role: UserRoleModel)
    suspend fun updateUserRole(role: UserRoleModel)
    suspend fun deleteUserRole(role: UserRoleModel)
}