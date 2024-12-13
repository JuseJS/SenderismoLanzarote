package org.iesharia.senderismolanzarote.domain.repository.user

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel

interface UserPreferencesRepository {
    suspend fun getUserPreferences(userId: Int): UserPreferencesModel?
    fun getAllPreferences(): Flow<List<UserPreferencesModel>>
    suspend fun insertUserPreferences(preferences: UserPreferencesModel)
    suspend fun updateUserPreferences(preferences: UserPreferencesModel)
    suspend fun deleteUserPreferences(preferences: UserPreferencesModel)
}