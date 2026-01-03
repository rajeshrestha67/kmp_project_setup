package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request

data class BankAccountValidationRequest(
    val destinationAccountNumber: String? = null,
    val destinationAccountName: String? = null,
    val destinationBankId: String? = null,
)
