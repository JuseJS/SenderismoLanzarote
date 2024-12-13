package org.iesharia.senderismolanzarote.data.mapper.route.reference

import org.iesharia.senderismolanzarote.data.database.entity.route.reference.PoiTypeEntity
import org.iesharia.senderismolanzarote.domain.model.route.reference.PoiTypeModel

fun PoiTypeEntity.toPoiType() = PoiTypeModel(
    id = id,
    type = type,
    description = description
)

fun PoiTypeModel.toPoiTypeEntity() = PoiTypeEntity(
    id = id,
    type = type,
    description = description
)