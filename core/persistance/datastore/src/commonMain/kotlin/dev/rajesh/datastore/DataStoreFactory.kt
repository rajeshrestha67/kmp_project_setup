package dev.rajesh.datastore

import dev.rajesh.datastore.token.local.TokenDataStore

expect class DataStoreFactory() {

    fun getSystemPath(jsonPath: String): String

    fun getTokenDataStore(jsonPath: String): TokenDataStore

    //fun getUserData(jsonPath: String): UserDataDataStore


}