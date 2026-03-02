package dev.rajesh.mobile_banking.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.rajesh.mobile_banking.database.models.UserDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface  UserDetailsDao {
    @Insert
    suspend fun insert(userDetailsEntity: UserDetailsEntity)

    @Query("SELECT * FROM UserDetailsEntity")
    fun getAll(): Flow<List<UserDetailsEntity>>

    @Delete
    suspend fun delete(userDetailsEntity: UserDetailsEntity)

    @Query("DELETE FROM UserDetailsEntity")
    suspend fun deleteAll()
}