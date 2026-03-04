package dev.rajesh.mobile_banking.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.rajesh.mobile_banking.database.models.CoopDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoopDetailDao {

    @Upsert
    suspend fun saveCoopDetail(coopDetailEntity: CoopDetailEntity)

    @Query("SELECT * FROM CoopDetailEntity WHERE clientId = :clientId LIMIT 1")
    fun getCoopDetailByClientId(clientId: String): Flow<CoopDetailEntity?>

    @Query("DELETE FROM CoopDetailEntity")
    fun deleteAllCoop()

    @Query("DELETE FROM CoopDetailEntity WHERE clientId = :clientId")
    fun deleteCoopById(clientId: String)
}