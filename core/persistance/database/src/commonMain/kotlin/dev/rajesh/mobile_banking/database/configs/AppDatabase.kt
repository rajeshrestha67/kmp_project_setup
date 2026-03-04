package dev.rajesh.mobile_banking.database.configs

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.rajesh.mobile_banking.database.dao.CoopBranchDetailDao
import dev.rajesh.mobile_banking.database.dao.CoopDetailDao
import dev.rajesh.mobile_banking.database.dao.UserDetailsDao
import dev.rajesh.mobile_banking.database.models.coop.CoopBranchDetailEntity
import dev.rajesh.mobile_banking.database.models.coop.CoopDetailEntity
import dev.rajesh.mobile_banking.database.models.userDetail.AccountDetailEntity
import dev.rajesh.mobile_banking.database.models.userDetail.QrEntity
import dev.rajesh.mobile_banking.database.models.userDetail.UserDetailsEntity

const val DB_NAME = "app_database.db"

@Database(
    entities =
        [
            UserDetailsEntity::class,
            AccountDetailEntity::class,
            QrEntity::class,
            CoopDetailEntity::class,
            CoopBranchDetailEntity::class
        ],
    version = 3
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDetailsDao(): UserDetailsDao
    abstract fun coopDetailsDao(): CoopDetailDao
    abstract fun coopBranchDetailDao(): CoopBranchDetailDao
}

//@Suppress("KotlinNoActualForExpect")
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}