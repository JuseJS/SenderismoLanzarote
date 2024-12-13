package org.iesharia.senderismolanzarote.data.mapper.route.main

import org.iesharia.senderismolanzarote.data.database.entity.route.main.NearbyServiceEntity
import org.iesharia.senderismolanzarote.domain.model.route.main.NearbyServiceModel

fun NearbyServiceEntity.toNearbyService(
    routeModel: org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel,
    serviceTypeModel: org.iesharia.senderismolanzarote.domain.model.route.reference.ServiceTypeModel
): NearbyServiceModel {
    return NearbyServiceModel(
        id = id,
        routeModel = routeModel,
        serviceTypeModel = serviceTypeModel,
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
        routeId = routeModel.id,
        serviceTypeId = serviceTypeModel.id,
        name = name,
        description = description,
        distanceMeters = distanceMeters,
        businessHours = businessHours,
        contactInfo = contactInfo
    )
}