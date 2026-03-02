package dev.rajesh.mobile_banking.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.rajesh.mobile_banking.database.models.AccountDetailEntity
import dev.rajesh.mobile_banking.database.models.QrEntity
import dev.rajesh.mobile_banking.database.models.UserDetailsEntity
import dev.rajesh.mobile_banking.database.relations.UserWithAccounts
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDetailsEntity: UserDetailsEntity)

    @Query("SELECT * FROM UserDetailsEntity")
    fun getAll(): Flow<List<UserDetailsEntity>>

    @Transaction
    @Query("SELECT * FROM USERDETAILSENTITY LIMIT 1")
    fun getUserWithAccounts(): Flow<UserWithAccounts?>

    @Delete
    suspend fun delete(userDetailsEntity: UserDetailsEntity)

    @Query("DELETE FROM UserDetailsEntity")
    suspend fun deleteAllUsers()


    /**
     * ACCOUNTS
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountDetailEntity>)

    @Query("DELETE FROM AccountDetailEntity")
    suspend fun deleteAllAccounts()

    /**
     * Qr
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQrs(qrList: List<QrEntity>)

    @Query("DELETE FROM QrEntity")
    suspend fun deleteAllQrs()

}