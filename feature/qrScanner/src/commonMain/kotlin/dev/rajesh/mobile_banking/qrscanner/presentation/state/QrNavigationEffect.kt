package dev.rajesh.mobile_banking.qrscanner.presentation.state

import dev.rajesh.mobile_banking.loadWallet.domain.model.QrWallet
import dev.rajesh.mobile_banking.qrscanner.domain.model.AccountDetails

sealed class QrNavigationEffect {
    data class ToInterbankTransfer(val accountDetails: AccountDetails) : QrNavigationEffect()
    data class ToSameBankTransfer(val accountDetails: AccountDetails) : QrNavigationEffect()
    data class ToWallet(val qrWallet: QrWallet) :
        QrNavigationEffect()

    data class ShowError(val message: String) : QrNavigationEffect()
}