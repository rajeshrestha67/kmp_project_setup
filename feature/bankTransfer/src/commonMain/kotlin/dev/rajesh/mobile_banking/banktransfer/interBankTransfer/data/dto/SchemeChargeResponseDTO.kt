package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class SchemeChargeResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val details: Double
)