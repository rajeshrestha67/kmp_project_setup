package dev.rajesh.room_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rajesh.room_database.entity.userDetail.QrEntity

@Dao
interface QrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQrCodes(qr: List<QrEntity>)

    @Query("SELECT * FROM QrEntity WHERE userMobileNumber = :mobile")
    suspend fun getQrCodes(mobile: String): List<QrEntity>
}