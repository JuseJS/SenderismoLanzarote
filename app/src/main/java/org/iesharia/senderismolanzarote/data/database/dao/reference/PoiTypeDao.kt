package org.iesharia.senderismolanzarote.data.database.dao.reference

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.route.reference.PoiType
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiTypeDao {
    @Query("SELECT * FROM poi_types")
    fun getAllPoiTypes(): Flow<List<PoiType>>

    @Query("SELECT * FROM poi_types WHERE id = :id")
    suspend fun getPoiTypeById(id: Int): PoiType?
}