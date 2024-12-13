package org.iesharia.senderismolanzarote.data.repository.route.reference

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.database.dao.route.reference.PoiTypeDao
import org.iesharia.senderismolanzarote.data.mapper.route.reference.toPoiType
import org.iesharia.senderismolanzarote.domain.model.route.reference.PoiTypeModel
import org.iesharia.senderismolanzarote.domain.repository.route.reference.PoiTypeRepository
import javax.inject.Inject

class PoiTypeRepositoryImpl @Inject constructor(
    private val poiTypeDao: PoiTypeDao
) : PoiTypeRepository {

    override fun getAllPoiTypes(): Flow<List<PoiTypeModel>> {
        return poiTypeDao.getAllPoiTypes().map { entities ->
            entities.map { it.toPoiType() }
        }
    }

    override suspend fun getPoiTypeById(id: Int): PoiTypeModel? {
        return poiTypeDao.getPoiTypeById(id)?.toPoiType()
    }
}