package org.iesharia.senderismolanzarote.domain.model.map

import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import java.math.BigDecimal

data class NavigationState(
    val isNavigating: Boolean = false,
    val currentRoute: RouteModel? = null,
    val userLatitude: BigDecimal? = null,
    val userLongitude: BigDecimal? = null
)