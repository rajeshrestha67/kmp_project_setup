package dev.rajesh.mobile_banking.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(
    val message: String? = null
)