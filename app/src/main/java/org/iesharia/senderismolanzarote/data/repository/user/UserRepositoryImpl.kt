package org.iesharia.senderismolanzarote.data.repository.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.user.UserDao
import org.iesharia.senderismolanzarote.data.mapper.user.toUser
import org.iesharia.senderismolanzarote.data.mapper.user.toUserEntity
import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import org.iesharia.senderismolanzarote.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override fun getAllUsers(): Flow<List<UserModel>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toUser() }
        }
    }

    override suspend fun getUserById(userId: Int): UserModel? {
        return userDao.getUserById(userId)?.toUser()
    }

    override suspend fun getUserByEmail(email: String): UserModel? {
        return userDao.getUserByEmail(email)?.toUser()
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