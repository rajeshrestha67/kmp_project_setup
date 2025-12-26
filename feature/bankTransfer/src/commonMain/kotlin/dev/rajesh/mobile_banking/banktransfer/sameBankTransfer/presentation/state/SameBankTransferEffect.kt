package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state

import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData

sealed interface SameBankTransferEffect {
    data class NavigateToConfirmation(
        val confirmationData: ConfirmationData
    ) : SameBankTransferEffect
}