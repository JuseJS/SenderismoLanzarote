package org.iesharia.senderismolanzarote.data.database.dao.route.reference

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.ServiceTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceTypeDao {
    @Query("SELECT * FROM service_types")
    fun getAllServiceTypes(): Flow<List<ServiceTypeEntity>>

    @Query("SELECT * FROM service_types WHERE id = :id")
    suspend fun getServiceTypeById(id: Int): ServiceTypeEntity?
}