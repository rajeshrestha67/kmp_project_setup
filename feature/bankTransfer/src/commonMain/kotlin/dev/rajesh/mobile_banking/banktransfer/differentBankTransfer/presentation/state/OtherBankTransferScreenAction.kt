package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.state

import org.jetbrains.compose.resources.StringResource

sealed interface OtherBankTransferScreenAction {

    data class OnReceiversAccountNumberChanged(val receiversAccountNumber: String) :
        OtherBankTransferScreenAction

    data class OnReceiversFullNameChanged(val receiversFullName: String) :
        OtherBankTransferScreenAction

    data class OnAmountChanged(val amount: String) : OtherBankTransferScreenAction
    data class OnRemarksChanged(val remarks: String) : OtherBankTransferScreenAction

    data object OnProceedClicked : OtherBankTransferScreenAction
    data object OnCancelClicked : OtherBankTransferScreenAction


    data class OnReceiversAccountNumberError(val receiversAccountNumberError: StringResource?) :
        OtherBankTransferScreenAction

    data class OnReceiversFullNameError(val receiversFullNameError: StringResource?) :
        OtherBankTransferScreenAction

    data class OnAmountError(val amountError: StringResource?) : OtherBankTransferScreenAction
    data class OnRemarksError(val remarksError: StringResource?) : OtherBankTransferScreenAction

}