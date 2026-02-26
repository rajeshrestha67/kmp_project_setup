package dev.rajesh.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.rajesh.datastore.token.local.TokenDataStore
import dev.rajesh.datastore.userData.datastore.UserDetailDataStore


const val DATASTORE_FILE_NAME = "settings.preferences_pb"

expect class DataStoreFactory() {

    fun getSystemPath(jsonPath: String): String

    fun getTokenDataStore(jsonPath: String): TokenDataStore

    fun getUserDetailsDS(jsonPath: String): UserDetailDataStore

    fun createDataStore(): DataStore<Preferences>

}