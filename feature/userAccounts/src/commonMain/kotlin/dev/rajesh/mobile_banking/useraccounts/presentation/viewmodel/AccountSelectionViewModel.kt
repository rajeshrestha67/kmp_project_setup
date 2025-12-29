package dev.rajesh.mobile_banking.useraccounts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import dev.rajesh.mobile_banking.useraccounts.presentation.state.AccountSelectionAction
import dev.rajesh.mobile_banking.useraccounts.presentation.state.AccountSelectionState
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountSelectionViewModel(
    private val fetchUserDetailUseCase: FetchUserDetailUseCase,
    private val selectedAccountStore: SelectedAccountStore

) : ViewModel() {
    private val _state = MutableStateFlow(AccountSelectionState())
    val state = _state.asStateFlow()

    init {
        loadAccounts()
        observeSelectedAccount()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            fetchUserDetailUseCase(false).onSuccess { userDetails ->
                val accounts = userDetails.accountDetail
                val selected = selectedAccountStore.selectedAccount.value ?: accounts.firstOrNull()
                selected?.let {
                    selectedAccountStore.set(it)
                }

                _state.update {
                    it.copy(
                        accounts = accounts,
                        selectedAccount = selected,
                        isLoading = false
                    )
                }

            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.toErrorMessage()
                    )
                }
            }

        }
    }

    private fun observeSelectedAccount() {
        viewModelScope.launch {
            selectedAccountStore.selectedAccount.collect { account ->
                _state.update {
                    it.copy(selectedAccount = account)
                }
            }
        }
    }

    fun onAction(action: AccountSelectionAction) {
        when (action) {
            is AccountSelectionAction.LoadAccounts -> {
                loadAccounts()
            }

            is AccountSelectionAction.SelectAccount -> {
                selectedAccountStore.set(action.account)
            }
        }
    }
}