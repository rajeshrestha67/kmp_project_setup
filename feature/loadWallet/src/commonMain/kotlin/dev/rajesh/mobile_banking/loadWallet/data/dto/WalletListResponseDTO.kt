package dev.rajesh.mobile_banking.loadWallet.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class WalletListResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val details: List<WalletDetailDTO>
)

@Serializable
data class WalletDetailDTO(
    val id: Int,
    val name: String,
    val descOneFieldName: String,
    val descOneFieldType: String,
    val descOneFixedLength: Boolean,
    val descOneLength: Int?,
    val descOneMinLength: Int?,
    val descOneMaxLength: Int?,
    val descTwoFieldName: String,
    val descTwoFieldType: String,
    val descTwoFixedLength: Boolean,
    val descTwoLength: String?,
    val descTwoMinLength: Int,
    val descTwoMaxLength: Int,
    val icon: String,
    val accountHead: String,
    val accountNumber: String,
    val minAmount: Double,
    val maxAmount: String?,
    val status: String
)
