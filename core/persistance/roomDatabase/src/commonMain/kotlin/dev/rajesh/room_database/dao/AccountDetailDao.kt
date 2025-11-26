package dev.rajesh.room_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rajesh.room_database.entity.userDetail.AccountDetailEntity

@Dao
interface AccountDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountDetailEntity>)

    @Query("Select * FROM AccountDetailEntity where userMobileNumber = :mobileNumber")
    suspend fun getAccounts(mobileNumber: String): List<AccountDetailEntity>
}