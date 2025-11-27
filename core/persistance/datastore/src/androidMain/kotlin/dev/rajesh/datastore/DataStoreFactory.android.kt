package dev.rajesh.datastore

import android.content.Context
import dev.rajesh.datastore.token.local.TokenDataStore
import dev.rajesh.datastore.userData.datastore.UserDetailDataStore
import dev.rajesh.datastore.userData.repository.UserDetailLocalDataSource
import org.koin.mp.KoinPlatform.getKoin

actual class DataStoreFactory {
    private val context: Context = getKoin().get()

    actual fun getSystemPath(jsonPath: String): String {
        return context.filesDir.resolve(
            "$jsonPath.json",
        ).absolutePath
    }

    actual fun getTokenDataStore(jsonPath: String): TokenDataStore {
        return TokenDataStore(
            produceFilePath = {
                getSystemPath(jsonPath)
            }
        )
    }
    //    fun getUserDetailsDS(jsonPath: String): UserDetailDataStore

    actual fun getUserDetailsDS(jsonPath: String): UserDetailDataStore {
        return UserDetailDataStore(
            filePath = {
                getSystemPath(jsonPath)
            }
        )
    }

}