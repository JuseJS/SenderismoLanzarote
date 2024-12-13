package org.iesharia.senderismolanzarote.data.database.dao.route.reference

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.DifficultyLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface DifficultyLevelDao {
    @Query("SELECT * FROM difficulty_levels")
    fun getAllDifficultyLevels(): Flow<List<DifficultyLevel>>

    @Query("SELECT * FROM difficulty_levels WHERE id = :id")
    suspend fun getDifficultyLevelById(id: Int): DifficultyLevel?
}