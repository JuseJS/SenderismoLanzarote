package org.iesharia.senderismolanzarote.data.mapper.user

import org.iesharia.senderismolanzarote.data.database.entity.user.FavoriteRouteEntity
import org.iesharia.senderismolanzarote.domain.model.user.FavoriteRouteModel

fun FavoriteRouteEntity.toFavoriteRoute(
    userModel: org.iesharia.senderismolanzarote.domain.model.user.UserModel,
    routeModel: org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
): FavoriteRouteModel {
    return FavoriteRouteModel(
        userModel = userModel,
        routeModel = routeModel,
        addedDate = addedDate
    )
}

fun FavoriteRouteModel.toFavoriteRouteEntity(): FavoriteRouteEntity {
    return FavoriteRouteEntity(
        userId = userModel.id,
        routeId = routeModel.id,
        addedDate = addedDate
    )
}