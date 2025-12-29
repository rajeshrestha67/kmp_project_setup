package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class FundTransferResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val detail: FundTransferDetailDTO
)

@Serializable
data class FundTransferDetailDTO(
    val transactionIdentifier: String
)