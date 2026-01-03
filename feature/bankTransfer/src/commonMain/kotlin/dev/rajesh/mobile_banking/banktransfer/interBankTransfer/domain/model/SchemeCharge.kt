package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class SchemeCharge(
    val status: String,
    val code: String,
    val message: String,
    val details: Double
)