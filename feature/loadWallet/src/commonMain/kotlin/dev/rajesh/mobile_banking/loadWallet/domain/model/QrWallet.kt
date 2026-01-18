package dev.rajesh.mobile_banking.loadWallet.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class QrWallet(
    val id: String,
    val name: String,
    val walletHolderName: String,
)