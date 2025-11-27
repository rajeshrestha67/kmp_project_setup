package dev.rajesh.datastore

import dev.rajesh.datastore.token.local.TokenDataStore
import dev.rajesh.datastore.userData.datastore.UserDetailDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DataStoreFactory {

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory).path!!
    }

    actual fun getSystemPath(jsonPath: String): String {
        return "${fileDirectory()}/$jsonPath.json"
    }

    actual fun getTokenDataStore(jsonPath: String): TokenDataStore {
        return TokenDataStore(
            produceFilePath = {
                getSystemPath(jsonPath)
            }
        )
    }

    actual fun getUserDetailsDS(jsonPath: String): UserDetailDataStore {
        return UserDetailDataStore(
            filePath = {
                getSystemPath(jsonPath)
            }
        )
    }


}