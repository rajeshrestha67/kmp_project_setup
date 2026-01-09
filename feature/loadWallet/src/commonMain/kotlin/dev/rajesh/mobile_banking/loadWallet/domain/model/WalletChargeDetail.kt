package dev.rajesh.mobile_banking.loadWallet.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class WalletChargeDetail(
    val code: String,
    val details: Double,
    val message: String,
    val status: String
)