package org.iesharia.senderismolanzarote.data.mapper.route.reference

import org.iesharia.senderismolanzarote.data.database.entity.route.reference.RouteStatusEntity
import org.iesharia.senderismolanzarote.domain.model.route.reference.RouteStatusModel

fun RouteStatusEntity.toRouteStatus() = RouteStatusModel(
    id = id,
    status = status,
    description = description
)

fun RouteStatusModel.toRouteStatusEntity() = RouteStatusEntity(
    id = id,
    status = status,
    description = description
)