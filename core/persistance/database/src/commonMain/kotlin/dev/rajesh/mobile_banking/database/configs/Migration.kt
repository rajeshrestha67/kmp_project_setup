package dev.rajesh.mobile_banking.database.configs

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(connection: SQLiteConnection) {
        //super.migrate(connection)
        connection.execSQL("ALTER TABLE 'CoopDetailEntity' ADD COLUMN 'email' TEXT NOT NULL DEFAULT ''")
    }
}