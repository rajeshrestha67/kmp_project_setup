package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request

data class FundTransferRequest(
    val fromAccountNumber: String,
    val toMobileNumber: String? = null,
    val toAccountNumber: String? = null, //receiver's account number
    val toAccountName: String? = null, // receiver's account name
    val bankBranchId: String? = null,

    val amount: String,
    val mPin: String,
    val remarks: String,
    val otp: String? = null
)