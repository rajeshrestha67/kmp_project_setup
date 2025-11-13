package dev.rajesh.mobile_banking.model.auth.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(
    val access_token: String? = null,
    val expires_in: Int? = null,
    val refresh_token: String? = null,
    val scope: String? = null,
    val token_type: String? = null
)

@Serializable
data class LoginRequestDTO(
    val client_id: String,
    val client_secret: String,
    val username: String,
    val password: String,
    val grant_type: String,
    val deviceUniqueIdentifier: String,
)

