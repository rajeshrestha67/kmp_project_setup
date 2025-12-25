package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail

sealed interface SelectBranchScreenAction {
    data class OnCoopBranchClicked(val coopBranchDetail: CoopBranchDetail) : SelectBranchScreenAction
}