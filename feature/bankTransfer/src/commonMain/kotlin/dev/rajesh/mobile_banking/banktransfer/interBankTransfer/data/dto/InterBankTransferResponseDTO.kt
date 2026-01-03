package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class InterBankTransferResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val details: String,
    val detail: InterBankTransferDetailDTO,
)


@Serializable
data class InterBankTransferDetailDTO(
    val transactionIdentifier: String,
    val status: String
)