package org.iesharia.senderismolanzarote.data.mapper.route.main

import org.iesharia.senderismolanzarote.data.database.entity.route.main.PointOfInterest as PointOfInterestEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.PointOfInterest as PointOfInterestModel

fun PointOfInterestEntity.toPointOfInterest(
    poiType: org.iesharia.senderismolanzarote.domain.model.route.reference.PoiType
): PointOfInterestModel {
    return PointOfInterestModel(
        id = id,
        routeId = routeId,
        name = name,
        type = poiType,
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
        routeId = routeId,
        name = name,
        typeId = type.id,
        description = description,
        latitude = latitude,
        longitude = longitude,
        accessibleReducedMobility = accessibleReducedMobility,
        estimatedVisitTime = estimatedVisitTime
    )
}
