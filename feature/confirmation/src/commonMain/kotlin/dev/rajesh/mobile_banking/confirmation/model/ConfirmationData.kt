package dev.rajesh.mobile_banking.confirmation.model

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationData(
    val title: String,
    val message: String,
    val transactionId: String? = null,
    val items: List<ConfirmationItem>
)