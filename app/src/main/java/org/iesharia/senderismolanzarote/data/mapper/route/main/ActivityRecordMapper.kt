package org.iesharia.senderismolanzarote.data.mapper.route.main

import org.iesharia.senderismolanzarote.data.database.entity.route.main.ActivityRecordEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.ActivityRecordModel

fun ActivityRecordEntity.toActivityRecord(
    userModel: org.iesharia.senderismolanzarote.domain.model.user.UserModel,
    routeModel: org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
): ActivityRecordModel {
    return ActivityRecordModel(
        id = id,
        userModel = userModel,
        routeModel = routeModel,
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
        userId = userModel.id,
        routeId = routeModel.id,
        startDate = startDate,
        endDate = endDate,
        totalTime = totalTime,
        actualDistanceKm = actualDistanceKm,
        comments = comments
    )
}