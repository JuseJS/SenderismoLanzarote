package org.iesharia.senderismolanzarote.domain.repository.route.reference

import kotlinx.coroutines.flow.Flow
import org.iesharia.senderismolanzarote.domain.model.route.reference.ServiceType

interface ServiceTypeRepository {
    fun getAllServiceTypes(): Flow<List<ServiceType>>
    suspend fun getServiceTypeById(id: Int): ServiceType?
}