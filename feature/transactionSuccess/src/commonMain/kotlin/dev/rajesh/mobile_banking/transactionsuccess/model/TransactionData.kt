package dev.rajesh.mobile_banking.transactionsuccess.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionData(
    val title: String,
    val message: String,
    val items: List<TransactionDataItem>
)
