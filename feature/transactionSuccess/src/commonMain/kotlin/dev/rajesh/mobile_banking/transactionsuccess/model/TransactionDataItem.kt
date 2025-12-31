package dev.rajesh.mobile_banking.transactionsuccess.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionDataItem(
    val label: String,
    val value: String
)