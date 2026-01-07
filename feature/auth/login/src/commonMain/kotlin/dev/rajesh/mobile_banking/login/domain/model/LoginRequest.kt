package dev.rajesh.mobile_banking.login.domain.model

data class LoginRequest(
    val username: String,
    val password: String,
    val clientId: String,
    val clientSecret: String,
    val grantType: String,
    val deviceUniqueIdentifier: String,
    val otp: String? = null
)
