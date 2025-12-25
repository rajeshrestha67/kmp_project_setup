package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request

data class AccountValidationRequest(
    val destinationAccountNumber: String? = null,
    val destinationAccountName: String?= null,
    val destinationBranchId: String?= null,
    val destinationMobileNumber: String?= null
)
