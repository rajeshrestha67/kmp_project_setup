package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request

data class BankTransferRequest(
    val sendersAccountNumber: String,
    val destinationBankId: String,
    val destinationBankName: String,
    val receiversAccountNumber: String,
    val receiversFullName: String,
    val amount: String,
    val charge: String,
    val remarks: String,
    val otp: String? = null,
    val mPin: String
)