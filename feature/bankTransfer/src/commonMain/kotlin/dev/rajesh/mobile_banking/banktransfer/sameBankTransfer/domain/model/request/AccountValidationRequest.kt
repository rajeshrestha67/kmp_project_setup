package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request

data class AccountValidationRequest(
    val destinationAccountNumber: String?,
    val destinationAccountName: String?,
    val destinationBranchId: String?,
    val mobileNumber: String?
)
