package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import org.jetbrains.compose.resources.StringResource

data class SameBankTransferState(
    val selectedTab: TransferTab = TransferTab.ACCOUNT,
    val fullName: String = "",
    val accountNumber: String = "",
    val branch: CoopBranchDetail? = null,
    val mobileNumber: String = "",
    val amount: String = "",
    val remarks: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val fullNameError: StringResource? = null,
    val accountNumberError: StringResource? = null,
    val branchError: StringResource? = null,
    val mobileNumberError: StringResource? = null,
    val amountError: StringResource? = null,
    val remarksError: StringResource? = null,

    val validatingAccount : Boolean  = false
)

enum class TransferTab {
    ACCOUNT,
    MOBILE
}