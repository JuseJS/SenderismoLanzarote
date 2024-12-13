package org.iesharia.senderismolanzarote.domain.repository.user

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferences

interface UserPreferencesRepository {
    suspend fun getUserPreferences(userId: Int): UserPreferences?
    fun getAllPreferences(): Flow<List<UserPreferences>>
    suspend fun insertUserPreferences(preferences: UserPreferences)
    suspend fun updateUserPreferences(preferences: UserPreferences)
    suspend fun deleteUserPreferences(preferences: UserPreferences)
}