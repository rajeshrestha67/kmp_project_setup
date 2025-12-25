package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccountValidationResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val detail: AccountValidationDetailDTO
)

@Serializable
data class AccountValidationDetailDTO(
    val status: String,
    val message: String,
    val matchPercentage: Double,
    val destinationAccountName: String
)