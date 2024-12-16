package org.iesharia.senderismolanzarote.data.mapper.user

import org.iesharia.senderismolanzarote.data.database.entity.user.UserPreferencesEntity
import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel

fun UserPreferencesEntity.toUserPreferences(
    userModel: org.iesharia.senderismolanzarote.domain.model.user.UserModel,
    difficultyLevelModel: DifficultyLevelModel
): UserPreferencesModel {
    return UserPreferencesModel(
        id = id,
        userModel = userModel,
        preferredDifficultyLevel = difficultyLevelModel,
        maxDistanceKm = maxDistanceKm,
        maxDurationMinutes = maxDurationMinutes
    )
}

fun UserPreferencesModel.toUserPreferencesEntity(): UserPreferencesEntity {
    return UserPreferencesEntity(
        id = id,
        userId = userModel.id,
        preferredDifficulty =  preferredDifficultyLevel.id,
        maxDistanceKm = maxDistanceKm,
        maxDurationMinutes = maxDurationMinutes
    )
}