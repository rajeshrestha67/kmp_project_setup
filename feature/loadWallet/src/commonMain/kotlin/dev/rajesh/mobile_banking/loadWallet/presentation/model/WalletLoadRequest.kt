package dev.rajesh.mobile_banking.loadWallet.presentation.model

data class WalletLoadRequest(
    val walletUsername: String,
    val remarks: String,
    val walletId: String,
    val senderAccountNumber: String,
    val amount: String,
    val validationIdentifier: String,
    val skipValidation: Boolean? = true
)
