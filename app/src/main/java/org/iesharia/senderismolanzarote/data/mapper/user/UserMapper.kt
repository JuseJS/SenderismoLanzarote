package org.iesharia.senderismolanzarote.data.mapper.user

import org.iesharia.senderismolanzarote.data.database.entity.user.UserEntity
import org.iesharia.senderismolanzarote.domain.model.user.UserModel
import org.iesharia.senderismolanzarote.domain.model.user.UserRoleModel

fun UserEntity.toUser(
    roleModel: UserRoleModel
): UserModel {
    return UserModel(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        registrationDate = registrationDate,
        roleModel = roleModel,
        profileImage = profileImage,
        passwordHash = passwordHash
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
        rolId = roleModel.id,
        profileImage = profileImage,
        passwordHash = passwordHash
    )
}