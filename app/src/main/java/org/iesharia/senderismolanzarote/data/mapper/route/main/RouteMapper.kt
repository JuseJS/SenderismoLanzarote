package org.iesharia.senderismolanzarote.data.mapper.route.main

import org.iesharia.senderismolanzarote.data.database.entity.route.main.RouteEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel

fun RouteEntity.toRoute(
    difficultyLevelModel: org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel,
    season: org.iesharia.senderismolanzarote.domain.model.route.reference.SeasonRouteModel,
    status: org.iesharia.senderismolanzarote.domain.model.route.reference.RouteStatusModel
): RouteModel {
    return RouteModel(
        id = id,
        name = name,
        difficultyLevelModel = difficultyLevelModel,
        season = season,
        status = status,
        startLatitude = startLatitude,
        startLongitude = startLongitude,
        endLatitude = endLatitude,
        endLongitude = endLongitude,
        distanceKm = distanceKm,
        estimatedDuration = estimatedDuration,
        elevationGain = elevationGain,
        description = description,
        startPoint = startPoint,
        endPoint = endPoint,
        lastReviewDate = lastReviewDate,
        statusSignage = statusSignage,
        isCircular = isCircular
    )
}

fun RouteModel.toRouteEntity(): RouteEntity {
    return RouteEntity(
        id = id,
        name = name,
        difficultyLevelId = difficultyLevelModel.id,
        seasonId = season.id,
        statusId = status.id,
        startLatitude = startLatitude,
        startLongitude = startLongitude,
        endLatitude = endLatitude,
        endLongitude = endLongitude,
        distanceKm = distanceKm,
        estimatedDuration = estimatedDuration,
        elevationGain = elevationGain,
        description = description,
        startPoint = startPoint,
        endPoint = endPoint,
        lastReviewDate = lastReviewDate,
        statusSignage = statusSignage,
        isCircular = isCircular
    )
}