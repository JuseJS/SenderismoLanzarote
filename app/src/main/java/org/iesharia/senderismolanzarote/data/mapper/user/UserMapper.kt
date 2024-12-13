package org.iesharia.senderismolanzarote.data.mapper.user

import org.iesharia.senderismolanzarote.data.database.entity.user.UserEntity
import org.iesharia.senderismolanzarote.domain.model.user.UserModel

fun UserEntity.toUser(): UserModel {
    return UserModel(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        registrationDate = registrationDate,
        roleId = rolId,
        profileImage = profileImage
    )
}


fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        registrationDate = registrationDate,
        rolId = roleId,
        profileImage = profileImage,
        passwordHash = "" // Este campo debe ser manejado en la capa del repositorio
    )
}