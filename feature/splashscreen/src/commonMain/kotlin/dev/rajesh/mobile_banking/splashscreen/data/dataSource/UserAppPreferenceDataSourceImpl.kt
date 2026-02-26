package dev.rajesh.mobile_banking.splashscreen.data.dataSource

import dev.rajesh.datastore.manager.DataStoreKeys
import dev.rajesh.datastore.manager.DataStoreManager
import dev.rajesh.mobile_banking.splashscreen.domain.model.UserAppPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class UserAppPreferenceDataSourceImpl(
    private val dataStoreManager: DataStoreManager
) : UserAppPreferenceDataSource {
    override fun getUserPreferences(): Flow<UserAppPreference> {
        return combine(
            dataStoreManager.get(DataStoreKeys.HAS_SHOWN_ON_BOARDING, false),
            dataStoreManager.get(DataStoreKeys.USER_THEME_MODE, 0),
            dataStoreManager.get(DataStoreKeys.LANG_CODE, "en")
        ) { hasShownOnBoarding, userThemeMode, langCode ->
            UserAppPreference(hasShownOnBoarding, userThemeMode, langCode)
        }
    }
}