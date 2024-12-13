package org.iesharia.senderismolanzarote.data.database.dao.user

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.user.UserPreferencesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferencesDao {
    @Query("SELECT * FROM user_preferences WHERE userId = :userId")
    suspend fun getUserPreferences(userId: Int): UserPreferencesEntity?

    @Query("SELECT * FROM user_preferences")
    fun getAllPreferences(): Flow<List<UserPreferencesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPreferences(preferences: UserPreferencesEntity)

    @Update
    suspend fun updateUserPreferences(preferences: UserPreferencesEntity)

    @Delete
    suspend fun deleteUserPreferences(preferences: UserPreferencesEntity)
}