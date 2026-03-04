package dev.rajesh.mobile_banking.database.configs

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        //super.migrate(connection)
        connection.execSQL("ALTER TABLE 'CoopDetailEntity' ADD COLUMN 'email' TEXT NOT NULL DEFAULT ''")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(connection: SQLiteConnection) {
        //super.migrate(connection)
        connection.execSQL(
            """
                CREATE TABLE IF NOT EXISTS `CoopBranchDetailEntity` (
                    `id` INTEGER PRIMARY KEY NOT NULL,
                    `name` TEXT NOT NULL,
                    `address` TEXT NOT NULL,
                    `branchCode` TEXT NOT NULL,
                    `bank` TEXT NOT NULL,
                    `city` TEXT NOT NULL,
                    `checker` INTEGER NOT NULL,
                    `maker` INTEGER NOT NULL,
                    `state` TEXT NOT NULL,
                    `bankId` INTEGER NOT NULL,
                    `bankCode` TEXT NOT NULL,
                    `cbsBranchCode` TEXT NOT NULL,
                    `email` TEXT NOT NULL,
                    `branchId` TEXT NOT NULL,
                    `latitude` TEXT NOT NULL,
                    `longitude` TEXT NOT NULL,
                    `nchl` TEXT NOT NULL,
                    `fax` TEXT NOT NULL,
                    `telephoneNumber` TEXT NOT NULL,
                    `branchManager` TEXT NOT NULL,
                    `createdDate` TEXT NOT NULL,
                    `status` TEXT NOT NULL,
                    `info` TEXT NOT NULL)""".trimIndent()
        )
    }
}