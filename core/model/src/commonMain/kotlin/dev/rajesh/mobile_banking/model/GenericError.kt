package dev.rajesh.mobile_banking.model

import kotlinx.serialization.Serializable

@Serializable
data class GenericError(
    val status: String? = null,
    val code: String? = null,
    val message: String? = null,
    val detail: GenericErrorDetail? = null
)

@Serializable
data class GenericErrorDetail(
    val status: String? = null,
    val message: String? = null
)