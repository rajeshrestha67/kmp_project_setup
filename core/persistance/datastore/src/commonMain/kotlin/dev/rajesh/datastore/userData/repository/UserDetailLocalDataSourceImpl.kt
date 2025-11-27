package dev.rajesh.datastore.userData.repository

import dev.rajesh.datastore.userData.datastore.UserDetailDataStore
import dev.rajesh.datastore.userData.model.UserDetailsLocal
import kotlinx.coroutines.flow.Flow

class UserDetailLocalDataSourceImpl(
    private val userDetailsDataStore: UserDetailDataStore
) : UserDetailLocalDataSource {
    override val userDetailsLocalFlow: Flow<UserDetailsLocal> = userDetailsDataStore.userDetailFlow

    override suspend fun saveUserDetails(userDetailsLocal: UserDetailsLocal) {
        userDetailsDataStore.update(userDetailsLocal)
    }
}