package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state

data class SameBankTransferState(
    val selectedTab: TransferTab = TransferTab.ACCOUNT,
    val fullName: String = "",
    val accountNumber: String = "",
    val mobileNumber: String = "",
    val amount: String = "",
    val remarks: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

enum class TransferTab {
    ACCOUNT,
    MOBILE
}