package org.iesharia.senderismolanzarote.data.repository.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.user.UserRoleDao
import org.iesharia.senderismolanzarote.data.mapper.user.toUserRole
import org.iesharia.senderismolanzarote.data.mapper.user.toUserRoleEntity
import org.iesharia.senderismolanzarote.domain.model.user.UserRoleModel
import org.iesharia.senderismolanzarote.domain.repository.user.UserRoleRepository
import javax.inject.Inject

class UserRoleRepositoryImpl @Inject constructor(
    private val userRoleDao: UserRoleDao
) : UserRoleRepository {

    override fun getAllUserRoles(): Flow<List<UserRoleModel>> {
        return userRoleDao.getAllUserRoles().map { entities ->
            entities.map { it.toUserRole() }
        }
    }

    override suspend fun getUserRoleById(roleId: Int): UserRoleModel? {
        return userRoleDao.getUserRoleById(roleId)?.toUserRole()
    }

    override suspend fun insertUserRole(role: UserRoleModel) {
        userRoleDao.insertUserRole(role.toUserRoleEntity())
    }

    override suspend fun updateUserRole(role: UserRoleModel) {
        userRoleDao.updateUserRole(role.toUserRoleEntity())
    }

    override suspend fun deleteUserRole(role: UserRoleModel) {
        userRoleDao.deleteUserRole(role.toUserRoleEntity())
    }
}