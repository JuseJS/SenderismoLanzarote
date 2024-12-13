package org.iesharia.senderismolanzarote.domain.model.route.main

import org.iesharia.senderismolanzarote.domain.model.route.reference.PoiTypeModel
import java.math.BigDecimal

data class PointOfInterestModel(
    val id: Int = 0,
    val routeModel: RouteModel,
    val name: String,
    val type: PoiTypeModel,
    val description: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val accessibleReducedMobility: Boolean = false,
    val estimatedVisitTime: Int = 0
)