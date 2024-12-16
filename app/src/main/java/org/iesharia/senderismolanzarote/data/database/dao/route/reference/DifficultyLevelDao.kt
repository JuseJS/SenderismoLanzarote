package org.iesharia.senderismolanzarote.data.database.dao.route.reference

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.DifficultyLevelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DifficultyLevelDao {
    @Query("SELECT * FROM difficulty_levels")
    fun getAllDifficultyLevels(): Flow<List<DifficultyLevelEntity>>

    @Query("SELECT * FROM difficulty_levels WHERE id = :id")
    suspend fun getDifficultyLevelById(id: Int): DifficultyLevelEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDifficultyLevel(difficultyLevel: DifficultyLevelEntity)
}