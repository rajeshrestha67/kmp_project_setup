package dev.rajesh.datastore.userData.repository

import dev.rajesh.datastore.userData.model.UserDetailsLocal
import kotlinx.coroutines.flow.Flow

interface UserDetailLocalDataSource {

    val userDetailsLocalFlow: Flow<UserDetailsLocal>

    suspend fun saveUserDetails(userDetailsLocal: UserDetailsLocal)
}