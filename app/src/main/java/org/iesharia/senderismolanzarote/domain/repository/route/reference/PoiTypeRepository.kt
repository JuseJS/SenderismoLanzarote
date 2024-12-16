package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.PoiTypeModel

interface PoiTypeRepository {
    fun getAllPoiTypes(): Flow<List<PoiTypeModel>>
    suspend fun getPoiTypeById(id: Int): PoiTypeModel?
    suspend fun insertPoiType(poiType: PoiTypeModel)
}