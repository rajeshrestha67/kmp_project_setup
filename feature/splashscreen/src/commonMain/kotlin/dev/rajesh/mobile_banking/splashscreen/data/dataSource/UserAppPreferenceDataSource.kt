package dev.rajesh.mobile_banking.splashscreen.data.dataSource

import dev.rajesh.mobile_banking.splashscreen.domain.model.UserAppPreference
import kotlinx.coroutines.flow.Flow


interface UserAppPreferenceDataSource{
    fun getUserPreferences(): Flow<UserAppPreference>
}