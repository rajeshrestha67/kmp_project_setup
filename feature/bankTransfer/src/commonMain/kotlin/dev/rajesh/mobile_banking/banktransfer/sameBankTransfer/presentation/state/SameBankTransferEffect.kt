package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state

import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionData

sealed interface SameBankTransferEffect {
    data class NavigateToConfirmation(
        val confirmationData: ConfirmationData
    ) : SameBankTransferEffect

    data class TransactionSuccessful(
        val transactionData: TransactionData
    ) : SameBankTransferEffect
}