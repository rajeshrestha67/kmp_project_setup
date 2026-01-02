package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class BankListResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val details: List<BankDetailsDTO>
)

@Serializable
data class BankDetailsDTO(
    val bankId: String,
    val refBankId: String? = null,
    val bankName: String,
    val enabled: String? = null,
    val lastModifiedOn: String? = null,
    val swiftCode: String? = null,
    val iconUrl: String? = null
)