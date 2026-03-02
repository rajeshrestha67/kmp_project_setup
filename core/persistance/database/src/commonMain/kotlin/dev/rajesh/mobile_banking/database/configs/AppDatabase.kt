package dev.rajesh.mobile_banking.database.configs

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.rajesh.mobile_banking.database.dao.UserDetailsDao
import dev.rajesh.mobile_banking.database.models.UserDetailsEntity

const val DB_NAME = "app_database.db"

@Database(
    entities = [UserDetailsEntity::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDetailsDao(): UserDetailsDao
}

//@Suppress("KotlinNoActualForExpect")
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}