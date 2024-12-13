package org.iesharia.senderismolanzarote.data.database.dao.user

import androidx.room.*
import org.iesharia.senderismolanzarote.data.database.entity.user.UserRole
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRoleDao {
    @Query("SELECT * FROM user_roles")
    fun getAllUserRoles(): Flow<List<UserRole>>

    @Query("SELECT * FROM user_roles WHERE id = :roleId")
    suspend fun getUserRoleById(roleId: Int): UserRole?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUserRole(role: UserRole)

    @Update
    suspend fun updateUserRole(role: UserRole)

    @Delete
    suspend fun deleteUserRole(role: UserRole)
}