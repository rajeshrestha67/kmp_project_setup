package dev.rajesh.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import dev.rajesh.datastore.token.local.TokenDataStore
import dev.rajesh.datastore.userData.datastore.UserDetailDataStore
import okio.Path.Companion.toPath
import org.koin.mp.KoinPlatform.getKoin

actual class DataStoreFactory {
    private val context: Context = getKoin().get()

    actual fun getSystemPath(jsonPath: String): String {
        return context.filesDir.resolve(
            "$jsonPath.json",
        ).absolutePath
    }

    actual fun createDataStore(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath.toPath()
            }
        )
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