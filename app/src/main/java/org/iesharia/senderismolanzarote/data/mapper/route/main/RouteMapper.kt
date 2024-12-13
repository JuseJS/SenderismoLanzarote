package org.iesharia.senderismolanzarote.data.mapper.route.main

import org.iesharia.senderismolanzarote.data.database.entity.route.main.Route as RouteEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.Route as RouteModel

fun RouteEntity.toRoute(
    difficultyLevel: org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevel,
    season: org.iesharia.senderismolanzarote.domain.model.route.reference.SeasonRoute,
    status: org.iesharia.senderismolanzarote.domain.model.route.reference.RouteStatus
): RouteModel {
    return RouteModel(
        id = id,
        name = name,
        difficultyLevel = difficultyLevel,
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
        difficultyLevelId = difficultyLevel.id,
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