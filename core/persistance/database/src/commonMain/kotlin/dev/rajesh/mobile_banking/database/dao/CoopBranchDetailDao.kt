package dev.rajesh.mobile_banking.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rajesh.mobile_banking.database.models.coop.CoopBranchDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoopBranchDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoopBranchDetail(coopBranchDetails: List<CoopBranchDetailEntity>)

    @Query("SELECT * FROM CoopBranchDetailEntity")
    fun getAllCoopBranchDetails(): Flow<List<CoopBranchDetailEntity>>

    @Query("DELETE FROM CoopBranchDetailEntity")
    suspend fun deleteAllCoopBranchDetails()

}