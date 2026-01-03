package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state

import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionData

interface InterBankTransferEffect {
    data class NavigateToConfirmation(
        val confirmationData: ConfirmationData
    ) : InterBankTransferEffect

    data class TransactionSuccessful(
        val transactionData: TransactionData
    ) : InterBankTransferEffect
}