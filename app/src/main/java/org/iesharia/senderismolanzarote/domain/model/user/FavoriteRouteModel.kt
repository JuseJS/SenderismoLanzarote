package org.iesharia.senderismolanzarote.domain.model.user

import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import java.time.LocalDateTime

data class FavoriteRouteModel(
    val userModel: UserModel,
    val routeModel: RouteModel,
    val addedDate: LocalDateTime = LocalDateTime.now()
)