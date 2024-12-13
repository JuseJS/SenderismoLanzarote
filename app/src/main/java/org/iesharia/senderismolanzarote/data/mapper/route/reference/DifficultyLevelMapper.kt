package org.iesharia.senderismolanzarote.data.mapper.route.reference

import org.iesharia.senderismolanzarote.data.database.entity.route.reference.DifficultyLevelEntity
import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel

fun DifficultyLevelEntity.toDifficultyLevel() = DifficultyLevelModel(
    id = id,
    name = name,
    description = description
)

fun DifficultyLevelModel.toDifficultyLevelEntity() = DifficultyLevelEntity(
    id = id,
    name = name,
    description = description
)