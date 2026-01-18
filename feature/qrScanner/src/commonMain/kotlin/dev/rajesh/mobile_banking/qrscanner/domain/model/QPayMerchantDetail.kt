package dev.rajesh.mobile_banking.qrscanner.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QPayMerchantDetail(
    val status: String,
    val bankTransfer: Boolean,
    val internalFundTransfer: Boolean,
    val loadWallet: Boolean,
    val accountDetails: AccountDetails?,
    val walletId: String?,
    val walletType: String?,
    val name: String?,
)

@Serializable
data class AccountDetails(
    val accountNumber: String,
    val accountName: String,
    val bankCode: String,
    val branchCode: String?,
    val bankName: String
)