package dev.rajesh.mobile_banking.loadWallet.presentation.state

import org.jetbrains.compose.resources.StringResource

sealed interface LoadWalletScreenAction {

    data class OnWalletIdChanged(val walletId: String): LoadWalletScreenAction
    data class OnAmountChanged(val amount: String): LoadWalletScreenAction
    data class OnRemarksChanged(val remarks: String): LoadWalletScreenAction

    data class OnWalletIdError(val walletIdError: StringResource?):LoadWalletScreenAction
    data class OnAmountError(val amountError: StringResource?):LoadWalletScreenAction
    data class OnRemarksError(val remarksError: StringResource?):LoadWalletScreenAction

    data class OnAccountNumberChanged(val accountNumber: String): LoadWalletScreenAction
    data class OnAccountNumberError(val accountNumberError: StringResource?): LoadWalletScreenAction

    data object OnProceedClicked: LoadWalletScreenAction


}