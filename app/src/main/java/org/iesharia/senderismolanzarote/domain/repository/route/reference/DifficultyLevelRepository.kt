package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel

interface DifficultyLevelRepository {
    fun getAllDifficultyLevels(): Flow<List<DifficultyLevelModel>>
    suspend fun getDifficultyLevelById(id: Int): DifficultyLevelModel?
    suspend fun insertDifficultyLevel(difficultyLevel: DifficultyLevelModel)
}