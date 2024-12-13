package org.iesharia.senderismolanzarote.data.repository.route.reference

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.ServiceTypeDao
import org.iesharia.senderismolanzarote.data.mapper.route.reference.toServiceType
import org.iesharia.senderismolanzarote.domain.model.route.reference.ServiceTypeModel
import org.iesharia.senderismolanzarote.domain.repository.route.reference.ServiceTypeRepository
import javax.inject.Inject

class ServiceTypeRepositoryImpl @Inject constructor(
    private val serviceTypeDao: ServiceTypeDao
) : ServiceTypeRepository {

    override fun getAllServiceTypes(): Flow<List<ServiceTypeModel>> {
        return serviceTypeDao.getAllServiceTypes().map { entities ->
            entities.map { it.toServiceType() }
        }
    }

    override suspend fun getServiceTypeById(id: Int): ServiceTypeModel? {
        return serviceTypeDao.getServiceTypeById(id)?.toServiceType()
    }
}