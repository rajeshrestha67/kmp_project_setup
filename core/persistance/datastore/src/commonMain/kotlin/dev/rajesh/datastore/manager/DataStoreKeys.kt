package dev.rajesh.datastore.manager

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val HAS_SHOWN_ON_BOARDING = booleanPreferencesKey("HAS_SHOWN_ON_BOARDING")
    val USER_THEME_MODE = intPreferencesKey("USER_THEME_MODE")
    val LANG_CODE = stringPreferencesKey("LANG_CODE")
}