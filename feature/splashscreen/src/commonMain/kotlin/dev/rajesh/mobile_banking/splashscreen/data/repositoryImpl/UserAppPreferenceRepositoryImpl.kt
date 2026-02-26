package dev.rajesh.mobile_banking.splashscreen.data.repositoryImpl

import dev.rajesh.datastore.manager.DataStoreKeys
import dev.rajesh.datastore.manager.DataStoreManager
import dev.rajesh.mobile_banking.splashscreen.data.dataSource.UserAppPreferenceDataSource
import dev.rajesh.mobile_banking.splashscreen.domain.UserAppPreferenceRepository
import dev.rajesh.mobile_banking.splashscreen.domain.model.UserAppPreference
import kotlinx.coroutines.flow.Flow

class UserAppPreferenceRepositoryImpl(
    private val userAppPreferenceDataSource: UserAppPreferenceDataSource,
    private val dataStoreManager: DataStoreManager
) : UserAppPreferenceRepository {
    override val userAppPreference: Flow<UserAppPreference>
        get() = userAppPreferenceDataSource.getUserPreferences()

    override suspend fun saveUserAppPreference(userAppPreference: UserAppPreference) {
        dataStoreManager.save(
            DataStoreKeys.HAS_SHOWN_ON_BOARDING,
            userAppPreference.hasShownOnBoarding ?: false
        )

        dataStoreManager.save(DataStoreKeys.USER_THEME_MODE, userAppPreference.userThemeMode)
        dataStoreManager.save(DataStoreKeys.LANG_CODE, userAppPreference.langCode)
    }
}