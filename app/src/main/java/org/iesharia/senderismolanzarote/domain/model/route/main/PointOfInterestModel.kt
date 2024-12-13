package org.iesharia.senderismolanzarote.domain.model.route.main

import org.iesharia.senderismolanzarote.domain.model.route.reference.PoiType
import java.math.BigDecimal

data class PointOfInterest(
    val id: Int = 0,
    val routeId: Int,
    val name: String,
    val type: PoiType,
    val description: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val accessibleReducedMobility: Boolean = false,
    val estimatedVisitTime: Int = 0
)