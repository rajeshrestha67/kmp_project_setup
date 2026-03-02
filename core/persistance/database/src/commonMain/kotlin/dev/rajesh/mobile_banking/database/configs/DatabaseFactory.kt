package dev.rajesh.mobile_banking.database.configs

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}