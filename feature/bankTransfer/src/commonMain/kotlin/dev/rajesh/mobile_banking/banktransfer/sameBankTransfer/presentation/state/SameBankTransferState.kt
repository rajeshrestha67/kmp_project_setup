package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import org.jetbrains.compose.resources.StringResource

data class SameBankTransferState(
    val selectedTab: TransferTab = TransferTab.ACCOUNT,
    val fullName: String = "",
    val accountNumber: String = "",
    val branch: CoopBranchDetail? = null,
    val mobileNumber: String = "9802304437",
    val amount: String = "",
    val remarks: String = "",
    val isLoading: Boolean = false,

    val fullNameError: StringResource? = null,
    val accountNumberError: StringResource? = null,
    val branchError: StringResource? = null,
    val mobileNumberError: StringResource? = null,
    val amountError: StringResource? = null,
    val remarksError: StringResource? = null,

    val errorMessage: String? = null,

    val validatingAccount: Boolean = false,
    val accountValidationError: AccountValidationError? = null,

    )

enum class TransferTab {
    ACCOUNT,
    MOBILE
}

data class AccountValidationError(
    val title: String,
    val message: String
)