package dev.rajesh.mobile_banking.splashscreen.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAppPreference(
    val hasShownOnBoarding: Boolean? = null,
    val userThemeMode: Int = 2,
    val langCode: String = "en"
)