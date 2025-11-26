package dev.rajesh.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.rajesh.room_database.dao.AccountDetailDao
import dev.rajesh.room_database.dao.QrDao
import dev.rajesh.room_database.dao.UserDetailsDao
import dev.rajesh.room_database.entity.userDetail.AccountDetailEntity
import dev.rajesh.room_database.entity.userDetail.QrEntity
import dev.rajesh.room_database.entity.userDetail.UserDetailEntity

@Database(
    entities = [
        UserDetailEntity::class,
        AccountDetailEntity::class,
        QrEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDetailDao(): UserDetailsDao
    abstract fun getAccountDetailDao(): AccountDetailDao
    abstract fun getQrDao(): QrDao


}