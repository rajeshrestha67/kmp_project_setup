package dev.rajesh.mobile_banking.loadWallet.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class WalletLoadResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val details: WalletLoadDetailsDTO
)

@Serializable
data class WalletLoadDetailsDTO(
    val descOneFieldName: String,
    val amount: String,
    val walletIcon: String,
    val walletName: String,
    val descTwoFieldValue: String,
    val descTwoFieldName: String,
    val transactionIdentifier: String,
    val descOneFieldValue: String,
    val accountNumber: String
)