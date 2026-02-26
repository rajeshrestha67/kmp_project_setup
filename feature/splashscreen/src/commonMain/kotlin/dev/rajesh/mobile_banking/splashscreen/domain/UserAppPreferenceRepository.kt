package dev.rajesh.mobile_banking.splashscreen.domain

import dev.rajesh.mobile_banking.splashscreen.domain.model.UserAppPreference
import kotlinx.coroutines.flow.Flow

interface UserAppPreferenceRepository {
    val userAppPreference: Flow<UserAppPreference>

    suspend fun saveUserAppPreference(userAppPreference: UserAppPreference)
}