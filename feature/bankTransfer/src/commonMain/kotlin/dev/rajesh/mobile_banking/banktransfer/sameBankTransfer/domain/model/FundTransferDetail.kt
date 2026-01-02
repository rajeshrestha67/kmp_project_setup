package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model

data class FundTransferDetail(
    val status: String,
    val transactionIdentifier: String,
    val message: String
)