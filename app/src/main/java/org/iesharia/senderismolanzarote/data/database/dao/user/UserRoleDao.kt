package org.iesharia.senderismolanzarote.data.database.dao.user

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.user.UserRoleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRoleDao {
    @Query("SELECT * FROM user_roles")
    fun getAllUserRoles(): Flow<List<UserRoleEntity>>

    @Query("SELECT * FROM user_roles WHERE id = :roleId")
    suspend fun getUserRoleById(roleId: Int): UserRoleEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUserRole(role: UserRoleEntity)

    @Update
    suspend fun updateUserRole(role: UserRoleEntity)

    @Delete
    suspend fun deleteUserRole(role: UserRoleEntity)
}