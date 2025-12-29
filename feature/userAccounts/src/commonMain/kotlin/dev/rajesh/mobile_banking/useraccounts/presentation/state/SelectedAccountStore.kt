package dev.rajesh.mobile_banking.useraccounts.presentation.state

import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedAccountStore {
    private val _selectedAccountState = MutableStateFlow<AccountDetail?>(null)
    val selectedAccount = _selectedAccountState.asStateFlow()

    fun set(account: AccountDetail) {
        _selectedAccountState.value = account
    }

    fun clear() {
        _selectedAccountState.value = null
    }
}