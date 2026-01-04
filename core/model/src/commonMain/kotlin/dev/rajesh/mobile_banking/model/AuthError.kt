package dev.rajesh.mobile_banking.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthError(
    val error: String? = null,
    val error_description: String? = null
)