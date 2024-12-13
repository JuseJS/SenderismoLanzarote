package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.ServiceTypeModel

interface ServiceTypeRepository {
    fun getAllServiceTypes(): Flow<List<ServiceTypeModel>>
    suspend fun getServiceTypeById(id: Int): ServiceTypeModel?
}