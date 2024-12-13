package org.iesharia.senderismolanzarote.domain.model.route.main

import org.iesharia.senderismolanzarote.domain.model.route.reference.ServiceType

data class NearbyService(
    val id: Int = 0,
    val routeId: Int,
    val serviceType: ServiceType,
    val name: String,
    val description: String,
    val distanceMeters: Int,
    val businessHours: String = "",
    val contactInfo: String = ""
)