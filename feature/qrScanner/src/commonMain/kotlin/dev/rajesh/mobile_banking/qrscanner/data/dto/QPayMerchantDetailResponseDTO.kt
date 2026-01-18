package dev.rajesh.mobile_banking.qrscanner.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class QPayMerchantDetailResponseDTO(
    val status: String,
    val code: String,
    val message: String?,
    val details: QPayMerchantDetailDTO
)

@Serializable
data class QPayMerchantDetailDTO(
    val status: String,
    val bankTransfer: Boolean?,
    val internalFundTransfer: Boolean?,
    val accountDetails: AccountDetailsDTO?,
    val walletId: String?,
    val walletType: String?,
    val loadWallet: Boolean?,
    val name: String?,

)

@Serializable
data class AccountDetailsDTO(
    val accountNumber: String,
    val accountName: String,
    val bankCode: String,
    val branchCode: String?,
    val bankName: String
)
