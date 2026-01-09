package dev.rajesh.mobile_banking.loadWallet.presentation.state

import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionData

interface WalletEffect {
    data class ToConfirmation(
        val confirmationData: ConfirmationData
    ) : WalletEffect

    data class ToTransactionSuccessful(
        val transactionData: TransactionData
    ) : WalletEffect
}