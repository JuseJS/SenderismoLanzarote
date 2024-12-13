package org.iesharia.senderismolanzarote.data.mapper.route.main

import org.iesharia.senderismolanzarote.data.database.entity.route.main.NearbyService as NearbyServiceEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.NearbyService as NearbyServiceModel

fun NearbyServiceEntity.toNearbyService(
    serviceType: org.iesharia.senderismolanzarote.domain.model.route.reference.ServiceType
): NearbyServiceModel {
    return NearbyServiceModel(
        id = id,
        routeId = routeId,
        serviceType = serviceType,
        name = name,
        description = description,
        distanceMeters = distanceMeters,
        businessHours = businessHours,
        contactInfo = contactInfo
    )
}

fun NearbyServiceModel.toNearbyServiceEntity(): NearbyServiceEntity {
    return NearbyServiceEntity(
        id = id,
        routeId = routeId,
        serviceTypeId = serviceType.id,
        name = name,
        description = description,
        distanceMeters = distanceMeters,
        businessHours = businessHours,
        contactInfo = contactInfo
    )
}