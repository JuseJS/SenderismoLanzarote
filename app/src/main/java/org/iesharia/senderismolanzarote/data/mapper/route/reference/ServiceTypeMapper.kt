package org.iesharia.senderismolanzarote.data.mapper.route.reference

import org.iesharia.senderismolanzarote.data.database.entity.route.reference.ServiceTypeEntity
import org.iesharia.senderismolanzarote.domain.model.route.reference.ServiceTypeModel

fun ServiceTypeEntity.toServiceType() = ServiceTypeModel(
    id = id,
    type = type,
    description = description
)

fun ServiceTypeModel.toServiceTypeEntity() = ServiceTypeEntity(
    id = id,
    type = type,
    description = description
)