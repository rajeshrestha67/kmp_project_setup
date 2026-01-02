package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.InterBankTransferScreenAction
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.InterBankTransferScreenState
import dev.rajesh.mobile_banking.logger.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class InterBankTransferViewModel() : ViewModel() {

    val _state = MutableStateFlow(InterBankTransferScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = InterBankTransferScreenState()
    )

    fun onAction(action: InterBankTransferScreenAction) {
        when (action) {
            is InterBankTransferScreenAction.OnBankSelected -> {
                _state.update {
                    AppLogger.i("OBTVM", "selectedBank Bank: ${action.selectedBank}")
                    it.copy(
                        selectedBank = action.selectedBank
                    )
                }
            }

            is InterBankTransferScreenAction.OnAmountChanged -> {
                _state.update {
                    it.copy(
                        amount = action.amount
                    )
                }
            }


            is InterBankTransferScreenAction.OnReceiversAccountNumberChanged -> {
                _state.update {
                    it.copy(
                        receiversAccountNumber = action.receiversAccountNumber
                    )
                }
            }

            is InterBankTransferScreenAction.OnReceiversFullNameChanged -> {
                _state.update {
                    it.copy(
                        receiversFullName = action.receiversFullName
                    )
                }
            }

            is InterBankTransferScreenAction.OnRemarksChanged -> {
                _state.update {
                    it.copy(
                        remarks = action.remarks
                    )
                }
            }

            is InterBankTransferScreenAction.OnReceiversAccountNumberError -> {
                _state.update {
                    it.copy(
                        receiversAccountNumberError = action.receiversAccountNumberError
                    )
                }
            }

            is InterBankTransferScreenAction.OnReceiversFullNameError -> {
                _state.update {
                    it.copy(
                        receiversFullNameError = action.receiversFullNameError
                    )
                }
            }

            is InterBankTransferScreenAction.OnAmountError -> {
                _state.update {
                    it.copy(
                        amountError = action.amountError
                    )
                }
            }

            is InterBankTransferScreenAction.OnRemarksError -> {
                _state.update {
                    it.copy(
                        remarksError = action.remarksError
                    )
                }
            }

            InterBankTransferScreenAction.OnProceedClicked -> {

            }

            InterBankTransferScreenAction.OnCancelClicked -> {

            }

        }

    }


}