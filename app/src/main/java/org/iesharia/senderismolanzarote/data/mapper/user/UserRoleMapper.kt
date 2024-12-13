package org.iesharia.senderismolanzarote.data.mapper.user

import org.iesharia.senderismolanzarote.data.database.entity.user.UserRoleEntity
import org.iesharia.senderismolanzarote.domain.model.user.UserRoleModel

fun UserRoleEntity.toUserRole(): UserRoleModel {
    return UserRoleModel(
        id = id,
        name = name,
        description = description
    )
}

fun UserRoleModel.toUserRoleEntity(): UserRoleEntity {
    return UserRoleEntity(
        id = id,
        name = name,
        description = description
    )
}