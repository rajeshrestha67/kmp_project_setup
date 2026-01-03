package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class InterBankTransferDetail(
    val transactionIdentifier: String,
    val status: String,
    val message: String
)