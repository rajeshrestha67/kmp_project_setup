package dev.rajesh.mobile_banking.database.configs

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(private val context: Context) {

    actual fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext

        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = DB_NAME,
        )
    }
}


