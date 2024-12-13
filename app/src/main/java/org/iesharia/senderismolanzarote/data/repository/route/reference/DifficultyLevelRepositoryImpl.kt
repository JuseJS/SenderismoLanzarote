package org.iesharia.senderismolanzarote.data.repository.route.reference

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.DifficultyLevelDao
import org.iesharia.senderismolanzarote.data.mapper.route.reference.toDifficultyLevel
import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel
import org.iesharia.senderismolanzarote.domain.repository.route.reference.DifficultyLevelRepository
import javax.inject.Inject

class DifficultyLevelRepositoryImpl @Inject constructor(
    private val difficultyLevelDao: DifficultyLevelDao
) : DifficultyLevelRepository {

    override fun getAllDifficultyLevels(): Flow<List<DifficultyLevelModel>> {
        return difficultyLevelDao.getAllDifficultyLevels().map { entities ->
            entities.map { it.toDifficultyLevel() }
        }
    }

    override suspend fun getDifficultyLevelById(id: Int): DifficultyLevelModel? {
        return difficultyLevelDao.getDifficultyLevelById(id)?.toDifficultyLevel()
    }
}