package org.iesharia.senderismolanzarote.domain.model.route.main

import org.iesharia.senderismolanzarote.domain.model.route.reference.ServiceTypeModel

data class NearbyServiceModel(
    val id: Int = 0,
    val routeModel: RouteModel,
    val serviceTypeModel: ServiceTypeModel,
    val name: String,
    val description: String,
    val distanceMeters: Int,
    val businessHours: String = "",
    val contactInfo: String = ""
)