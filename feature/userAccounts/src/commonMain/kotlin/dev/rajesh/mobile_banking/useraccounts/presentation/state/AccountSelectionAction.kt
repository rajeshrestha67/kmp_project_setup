package dev.rajesh.mobile_banking.useraccounts.presentation.state

import dev.rajesh.mobile_banking.user.domain.model.AccountDetail

sealed interface AccountSelectionAction {
    data object LoadAccounts : AccountSelectionAction
    data class SelectAccount(val account: AccountDetail) : AccountSelectionAction
}