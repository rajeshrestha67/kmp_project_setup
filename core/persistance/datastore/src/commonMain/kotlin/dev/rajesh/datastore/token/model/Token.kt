package dev.rajesh.datastore.token.model

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val jwtToken: String? = null,
    val biometricToken: String? = null,
    val isBiometricEnable: Boolean = false,
    val fcmToken: String? = null,
    val mPin: String? = null,
)
