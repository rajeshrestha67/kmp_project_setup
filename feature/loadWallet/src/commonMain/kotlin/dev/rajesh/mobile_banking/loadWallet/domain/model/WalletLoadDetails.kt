package dev.rajesh.mobile_banking.loadWallet.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class WalletLoadDetails(
    val message: String,
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