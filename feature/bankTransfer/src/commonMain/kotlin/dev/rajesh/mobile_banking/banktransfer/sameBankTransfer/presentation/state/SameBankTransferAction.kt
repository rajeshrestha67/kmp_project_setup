package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state

import org.jetbrains.compose.resources.StringResource

sealed interface SameBankTransferAction {
    data class OnFullNameChanged(val fullName: String): SameBankTransferAction
    data class OnAccountNumberChanged(val accountNumber: String): SameBankTransferAction
    data class OnBranchChanged(val branch: String): SameBankTransferAction
    data class OnMobileNumberChanged(val mobileNumber: String): SameBankTransferAction
    data class OnAmountChanged(val amount: String): SameBankTransferAction
    data class OnRemarksChanged(val remarks: String): SameBankTransferAction
    data object OnProceedClicked: SameBankTransferAction


    data class OnFullNameError(val fullNameError: StringResource?): SameBankTransferAction
    data class OnAccountNumberError(val accountNumberError: StringResource?): SameBankTransferAction
    data class OnBranchError(val branchError: StringResource?): SameBankTransferAction
    data class OnMobileNumberError(val mobileNumberError: StringResource?): SameBankTransferAction
    data class OnAmountError(val amountError: StringResource?): SameBankTransferAction
    data class OnRemarksError(val remarksError: StringResource?): SameBankTransferAction



}