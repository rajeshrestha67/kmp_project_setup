package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BankAccountValidationResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val detail: BankAccountValidationDetailDTO
)

@Serializable
data class BankAccountValidationDetailDTO(
    val status: String,
    val message: String,
    val matchPercentage: Double,
    val destinationAccountName: String?
)