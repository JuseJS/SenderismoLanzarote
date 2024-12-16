package org.iesharia.senderismolanzarote.data.repository.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.user.UserDao
import org.iesharia.senderismolanzarote.data.mapper.user.toUser
import org.iesharia.senderismolanzarote.data.mapper.user.toUserEntity
import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import org.iesharia.senderismolanzarote.domain.repository.user.UserRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserRoleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userRoleRepository: UserRoleRepository
) : UserRepository {

    override fun getAllUsers(): Flow<List<UserModel>> {
        return userDao.getAllUsers().map { entities ->
            entities.mapNotNull { entity ->
                userRoleRepository.getUserRoleById(entity.rolId)?.let { role ->
                    entity.toUser(role)
                }
            }
        }
    }

    override suspend fun getUserById(userId: Int): UserModel? {
        val userEntity = userDao.getUserById(userId)
        return if (userEntity != null) {
            userRoleRepository.getUserRoleById(userEntity.rolId)?.let { role ->
                userEntity.toUser(role)
            }
        } else null
    }

    override suspend fun getUserByEmail(email: String): UserModel? {
        val userEntity = userDao.getUserByEmail(email)
        return if (userEntity != null) {
            userRoleRepository.getUserRoleById(userEntity.rolId)?.let { role ->
                userEntity.toUser(role)
            }
        } else null
    }

    override suspend fun insertUser(user: UserModel): Long {
        return userDao.insertUser(user.toUserEntity())
    }

    override suspend fun updateUser(user: UserModel) {
        userDao.updateUser(user.toUserEntity())
    }

    override suspend fun deleteUser(user: UserModel) {
        userDao.deleteUser(user.toUserEntity())
    }
}