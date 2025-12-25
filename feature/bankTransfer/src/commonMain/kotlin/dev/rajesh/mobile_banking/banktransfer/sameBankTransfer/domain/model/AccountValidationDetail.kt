package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountValidationDetail(
    val status: String,
    val message: String,
    val matchPercentage: Double,
    val destinationAccountName: String
)