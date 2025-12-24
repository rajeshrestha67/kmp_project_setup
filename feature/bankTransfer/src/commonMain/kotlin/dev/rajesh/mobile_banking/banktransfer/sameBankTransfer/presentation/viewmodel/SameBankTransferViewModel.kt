package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferAction
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferState
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.TransferTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SameBankTransferViewModel : ViewModel() {

    private val _state = MutableStateFlow(SameBankTransferState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SameBankTransferState()
    )

    fun onTabSelected(tab: TransferTab) {
        _state.update { it.copy(selectedTab = tab) }
    }

    fun onAction(action: SameBankTransferAction) {
        when (action) {
            is SameBankTransferAction.OnFullNameChanged -> {
                _state.update {
                    it.copy(fullName = action.fullName)
                }
            }

            is SameBankTransferAction.OnAccountNumberChanged -> {
                _state.update {
                    it.copy(accountNumber = action.accountNumber)
                }
            }

            is SameBankTransferAction.OnBranchChanged -> {
                _state.update {
                    it.copy(branch = action.branch)
                }
            }

            is SameBankTransferAction.OnMobileNumberChanged -> {
                _state.update {
                    it.copy(mobileNumber = action.mobileNumber)
                }
            }

            is SameBankTransferAction.OnAmountChanged -> {
                _state.update {
                    it.copy(amount = action.amount)
                }
            }

            is SameBankTransferAction.OnRemarksChanged -> {
                _state.update {
                    it.copy(remarks = action.remarks)
                }
            }

            SameBankTransferAction.OnProceedClicked -> proceedForValidation()

            is SameBankTransferAction.OnAccountNumberError -> {
                _state.update {
                    it.copy(accountNumberError = action.accountNumberError)
                }
            }

            is SameBankTransferAction.OnAmountError -> {
                _state.update {
                    it.copy(amountError = action.amountError)
                }
            }

            is SameBankTransferAction.OnBranchError -> {
                _state.update {
                    it.copy(branchError = action.branchError)
                }
            }

            is SameBankTransferAction.OnFullNameError -> {
                _state.update {
                    it.copy(fullNameError = action.fullNameError)
                }
            }

            is SameBankTransferAction.OnMobileNumberError -> {
                _state.update {
                    it.copy(mobileNumberError = action.mobileNumberError)
                }
            }

            is SameBankTransferAction.OnRemarksError -> {
                _state.update {
                    it.copy(remarksError = action.remarksError)
                }
            }
        }
    }

    private fun proceedForValidation() = viewModelScope.launch {

    }
}