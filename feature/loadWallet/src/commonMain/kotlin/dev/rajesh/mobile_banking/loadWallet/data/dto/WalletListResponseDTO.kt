package dev.rajesh.mobile_banking.loadWallet.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class WalletListResponseDTO(
    val status: String? = null,
    val code: String? = null,
    val message: String? = null,
    val details: List<WalletDetailDTO>? = null
)

@Serializable
data class WalletDetailDTO(
    val id: Int? = null,
    val name: String? = null,
    val descOneFieldName: String? = null,
    val descOneFieldType: String? = null,
    val descOneFixedLength: Boolean? = null,
    val descOneLength: Int? = null,
    val descOneMinLength: Int? = null,
    val descOneMaxLength: Int? = null,
    val descTwoFieldName: String? = null,
    val descTwoFieldType: String? = null,
    val descTwoFixedLength: Boolean? = null,
    val descTwoLength: String? = null,
    val descTwoMinLength: Int? = null,
    val descTwoMaxLength: Int? = null,
    val icon: String? = null,
    val accountHead: String? = null,
    val accountNumber: String? = null,
    val minAmount: Double? = null,
    val maxAmount: Double? = null,
    val status: String? = null
)
