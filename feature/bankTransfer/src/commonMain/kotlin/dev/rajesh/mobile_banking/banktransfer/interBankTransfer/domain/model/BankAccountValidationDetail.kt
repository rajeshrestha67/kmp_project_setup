package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BankAccountValidationDetail(
    val status: String,
    val message: String,
    val matchPercentage: Double,
    val destinationAccountName: String
)