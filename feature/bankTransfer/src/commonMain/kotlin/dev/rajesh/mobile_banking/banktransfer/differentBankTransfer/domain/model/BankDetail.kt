package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BankDetail(
    val bankId: String,
    val refBankId: String,
    val bankName: String,
    val enabled: Boolean,
    val lastModifiedOn: String,
    val swiftCode: String,
    val iconUrl: String
)