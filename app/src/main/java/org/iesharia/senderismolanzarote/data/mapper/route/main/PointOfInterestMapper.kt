package org.iesharia.senderismolanzarote.data.mapper.route.main

import org.iesharia.senderismolanzarote.data.database.entity.route.main.PointOfInterestEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.PointOfInterestModel

fun PointOfInterestEntity.toPointOfInterest(
    routeModel: org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel,
    poiTypeModel: org.iesharia.senderismolanzarote.domain.model.route.reference.PoiTypeModel
): PointOfInterestModel {
    return PointOfInterestModel(
        id = id,
        routeModel = routeModel,
        name = name,
        type = poiTypeModel,
        description = description,
        latitude = latitude,
        longitude = longitude,
        accessibleReducedMobility = accessibleReducedMobility,
        estimatedVisitTime = estimatedVisitTime
    )
}

fun PointOfInterestModel.toPointOfInterestEntity(): PointOfInterestEntity {
    return PointOfInterestEntity(
        id = id,
        routeId = routeModel.id,
        name = name,
        typeId = type.id,
        description = description,
        latitude = latitude,
        longitude = longitude,
        accessibleReducedMobility = accessibleReducedMobility,
        estimatedVisitTime = estimatedVisitTime
    )
}
