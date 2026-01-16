package dev.rajesh.mobile_banking.qrscanner.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QPayMerchantDetail(
    val bankTransfer: Boolean,
    val accountDetails: AccountDetails,
    val status: String
)

@Serializable
data class AccountDetails(
    val accountNumber: String,
    val accountName: String,
    val bankCode: String,
    val branchCode: String?,
    val bankName: String
)