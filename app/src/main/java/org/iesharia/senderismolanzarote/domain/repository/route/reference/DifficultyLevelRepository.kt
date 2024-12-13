package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevel

interface DifficultyLevelRepository {
    fun getAllDifficultyLevels(): Flow<List<DifficultyLevel>>
    suspend fun getDifficultyLevelById(id: Int): DifficultyLevel?
}