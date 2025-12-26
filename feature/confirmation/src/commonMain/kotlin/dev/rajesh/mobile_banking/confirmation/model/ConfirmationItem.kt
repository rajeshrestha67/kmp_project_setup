package dev.rajesh.mobile_banking.confirmation.model

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationItem(
    val label: String,
    val value: String
)