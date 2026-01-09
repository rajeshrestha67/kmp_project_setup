package dev.rajesh.mobile_banking.loadWallet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WalletValidationDetail(
    val message: String,
    val status: String,
    val customerName: String?,
    val customerProfileImageUrl: String?,
    val validationIdentifier: String?
)