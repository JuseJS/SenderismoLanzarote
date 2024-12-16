package org.iesharia.senderismolanzarote.data.repository.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.user.UserPreferencesDao
import org.iesharia.senderismolanzarote.data.mapper.user.toUserPreferences
import org.iesharia.senderismolanzarote.data.mapper.user.toUserPreferencesEntity
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel
import org.iesharia.senderismolanzarote.domain.repository.user.UserPreferencesRepository
import org.iesharia.senderismolanzarote.domain.repository.user.UserRepository
import org.iesharia.senderismolanzarote.domain.repository.route.reference.DifficultyLevelRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesDao: UserPreferencesDao,
    private val userRepository: UserRepository,
    private val difficultyLevelRepository: DifficultyLevelRepository
) : UserPreferencesRepository {

    override suspend fun getUserPreferences(userId: Int): UserPreferencesModel? {
        val preferencesEntity = userPreferencesDao.getUserPreferences(userId)
        if (preferencesEntity != null) {
            val user = userRepository.getUserById(preferencesEntity.userId)
            val difficultyLevel = difficultyLevelRepository.getDifficultyLevelById(preferencesEntity.preferredDifficulty)
            if (user != null && difficultyLevel != null) {
                return preferencesEntity.toUserPreferences(user, difficultyLevel)
            }
        }
        return null
    }

    override fun getAllPreferences(): Flow<List<UserPreferencesModel>> {
        return userPreferencesDao.getAllPreferences().map { entities ->
            entities.mapNotNull { entity ->
                val user = userRepository.getUserById(entity.userId)
                val difficultyLevel = difficultyLevelRepository.getDifficultyLevelById(entity.preferredDifficulty)
                if (user != null && difficultyLevel != null) {
                    entity.toUserPreferences(user, difficultyLevel)
                } else null
            }
        }
    }

    override suspend fun insertUserPreferences(preferences: UserPreferencesModel) {
        userPreferencesDao.insertUserPreferences(preferences.toUserPreferencesEntity())
    }

    override suspend fun updateUserPreferences(preferences: UserPreferencesModel) {
        userPreferencesDao.updateUserPreferences(preferences.toUserPreferencesEntity())
    }

    override suspend fun deleteUserPreferences(preferences: UserPreferencesModel) {
        userPreferencesDao.deleteUserPreferences(preferences.toUserPreferencesEntity())
    }
}