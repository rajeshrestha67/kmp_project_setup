package dev.rajesh.mobile_banking.useraccounts.presentation.state

import dev.rajesh.mobile_banking.user.domain.model.AccountDetail

data class AccountSelectionState(
    val accounts: List<AccountDetail> = emptyList(),
    val selectedAccount: AccountDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
