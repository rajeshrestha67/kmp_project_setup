package dev.rajesh.mobile_banking.loadWallet.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class WalletValidationResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val detail: WalletValidationDetailDTO,
)

@Serializable
data class WalletValidationDetailDTO(
    val message: String,
    val status: String,
    val customerName: String?,
    val customerProfileImageUrl: String?,
    val validationIdentifier: String?
)