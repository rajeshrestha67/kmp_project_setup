package dev.rajesh.room_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rajesh.room_database.entity.userDetail.UserDetailEntity

@Dao
interface UserDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDetailEntity)

    @Query("SELECT * FROM UserDetailEntity WHERE mobileNumber = :mobileNumber")
    suspend fun getUser(mobileNumber: String): UserDetailEntity?


}