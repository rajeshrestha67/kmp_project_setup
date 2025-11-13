package dev.rajesh.mobile_banking.domain.auth.login.model

data class LoginData(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val token_type: String
)