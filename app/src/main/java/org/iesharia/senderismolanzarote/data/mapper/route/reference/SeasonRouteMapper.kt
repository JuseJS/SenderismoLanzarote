package org.iesharia.senderismolanzarote.data.mapper.route.reference

import org.iesharia.senderismolanzarote.data.database.entity.route.reference.SeasonRouteEntity
import org.iesharia.senderismolanzarote.domain.model.route.reference.SeasonRouteModel

fun SeasonRouteEntity.toSeasonRoute() = SeasonRouteModel(
    id = id,
    season = season,
    description = description
)

fun SeasonRouteModel.toSeasonRouteEntity() = SeasonRouteEntity(
    id = id,
    season = season,
    description = description
)