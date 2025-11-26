package dev.rajesh.room_database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory
//
//fun getAppDatabase(): AppDatabase {
//    val dbFile = NSHomeDirectory() + "appDatabase.db"
//    return Room.databaseBuilder<AppDatabase>(
//        name = dbFile,
//        factory = { AppDatabase::class.instantiateImpl() }
//    ).setDriver(BundledSQLiteDriver())
//        .build()
//}