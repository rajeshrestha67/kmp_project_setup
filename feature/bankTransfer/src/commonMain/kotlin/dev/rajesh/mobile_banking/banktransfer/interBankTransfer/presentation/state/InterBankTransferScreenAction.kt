package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import org.jetbrains.compose.resources.StringResource

sealed interface InterBankTransferScreenAction {


    data class InitFromNavigation(
        val accountNumber: String?,
        val accountName: String?,
        val bank: BankDetail?
    ) : InterBankTransferScreenAction

    data class OnBankSelected(val selectedBank: BankDetail) : InterBankTransferScreenAction

    data class OnReceiversAccountNumberChanged(val receiversAccountNumber: String) :
        InterBankTransferScreenAction

    data class OnReceiversFullNameChanged(val receiversFullName: String) :
        InterBankTransferScreenAction

    data class OnAmountChanged(val amount: String) : InterBankTransferScreenAction
    data class OnRemarksChanged(val remarks: String) : InterBankTransferScreenAction

    data object OnProceedClicked : InterBankTransferScreenAction
    data object OnCancelClicked : InterBankTransferScreenAction

    data class OnReceiversAccountNumberError(val receiversAccountNumberError: StringResource?) :
        InterBankTransferScreenAction

    data class OnReceiversFullNameError(val receiversFullNameError: StringResource?) :
        InterBankTransferScreenAction

    data class OnAmountError(val amountError: StringResource?) : InterBankTransferScreenAction
    data class OnRemarksError(val remarksError: StringResource?) : InterBankTransferScreenAction

}