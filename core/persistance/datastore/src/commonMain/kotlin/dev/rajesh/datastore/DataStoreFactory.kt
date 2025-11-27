package dev.rajesh.datastore

import dev.rajesh.datastore.token.local.TokenDataStore
import dev.rajesh.datastore.userData.datastore.UserDetailDataStore

expect class DataStoreFactory() {

    fun getSystemPath(jsonPath: String): String

    fun getTokenDataStore(jsonPath: String): TokenDataStore

    fun getUserDetailsDS(jsonPath: String): UserDetailDataStore


}