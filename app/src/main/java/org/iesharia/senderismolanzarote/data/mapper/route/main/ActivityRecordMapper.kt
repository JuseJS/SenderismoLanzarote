package org.iesharia.senderismolanzarote.data.mapper.route.main

import org.iesharia.senderismolanzarote.data.database.entity.route.main.ActivityRecord as ActivityRecordEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.ActivityRecord as ActivityRecordModel

fun ActivityRecordEntity.toActivityRecord(route: org.iesharia.senderismolanzarote.domain.model.route.main.Route): ActivityRecordModel {
    return ActivityRecordModel(
        id = id,
        userId = userId,
        route = route,
        startDate = startDate,
        endDate = endDate,
        totalTime = totalTime,
        actualDistanceKm = actualDistanceKm,
        comments = comments
    )
}

fun ActivityRecordModel.toActivityRecordEntity(): ActivityRecordEntity {
    return ActivityRecordEntity(
        id = id,
        userId = userId,
        routeId = route.id,
        startDate = startDate,
        endDate = endDate,
        totalTime = totalTime,
        actualDistanceKm = actualDistanceKm,
        comments = comments
    )
}