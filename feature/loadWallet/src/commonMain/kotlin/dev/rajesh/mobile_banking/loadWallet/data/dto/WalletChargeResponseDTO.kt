package dev.rajesh.mobile_banking.loadWallet.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class WalletChargeResponseDTO(
    val code: String,
    val details: Double,
    val message: String,
    val status: String
)