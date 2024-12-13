package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.PoiType

interface PoiTypeRepository {
    fun getAllPoiTypes(): Flow<List<PoiType>>
    suspend fun getPoiTypeById(id: Int): PoiType?
}