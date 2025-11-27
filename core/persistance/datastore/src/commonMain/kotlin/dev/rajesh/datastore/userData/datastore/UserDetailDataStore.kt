package dev.rajesh.datastore.userData.datastore

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import dev.rajesh.datastore.userData.model.UserDetailsLocal
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

class UserDetailDataStore(
    private val filePath: () -> String
) {

    private val dataStore = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = UserDetailSerializer,
            producePath = {
                filePath().toPath()
            }
        )
    )


    val userDetailFlow: Flow<UserDetailsLocal>
        get() = dataStore.data

    suspend fun update(userDetailsLocal: UserDetailsLocal) {
        dataStore.updateData { _ ->
            userDetailsLocal
        }
    }

}