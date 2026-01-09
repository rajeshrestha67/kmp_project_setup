package dev.rajesh.mobile_banking.loadWallet.presentation.state

import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import org.jetbrains.compose.resources.StringResource

sealed interface LoadWalletScreenAction {

    data class OnWalletIdChanged(val walletId: String): LoadWalletScreenAction
    data class OnAmountChanged(val amount: String): LoadWalletScreenAction
    data class OnRemarksChanged(val remarks: String): LoadWalletScreenAction

    data class OnWalletIdError(val walletIdError: StringResource?):LoadWalletScreenAction
    data class OnAmountError(val amountError: StringResource?):LoadWalletScreenAction
    data class OnRemarksError(val remarksError: StringResource?):LoadWalletScreenAction

    data class OnProceedClicked(val walletDetail: WalletDetail): LoadWalletScreenAction


}