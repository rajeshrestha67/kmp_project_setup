package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.state.OtherBankTransferScreenAction
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.state.OtherBankTransferScreenState
import dev.rajesh.mobile_banking.logger.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class OtherBankTransferViewModel() : ViewModel() {

    val _state = MutableStateFlow(OtherBankTransferScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OtherBankTransferScreenState()
    )

    fun onAction(action: OtherBankTransferScreenAction) {
        when (action) {
            is OtherBankTransferScreenAction.OnBankSelected -> {
                _state.update {
                    AppLogger.i("OBTVM", "selectedBank Bank: ${action.selectedBank}")
                    it.copy(
                        selectedBank = action.selectedBank
                    )
                }
            }

            is OtherBankTransferScreenAction.OnAmountChanged -> {
                _state.update {
                    it.copy(
                        amount = action.amount
                    )
                }
            }


            is OtherBankTransferScreenAction.OnReceiversAccountNumberChanged -> {
                _state.update {
                    it.copy(
                        receiversAccountNumber = action.receiversAccountNumber
                    )
                }
            }

            is OtherBankTransferScreenAction.OnReceiversFullNameChanged -> {
                _state.update {
                    it.copy(
                        receiversFullName = action.receiversFullName
                    )
                }
            }

            is OtherBankTransferScreenAction.OnRemarksChanged -> {
                _state.update {
                    it.copy(
                        remarks = action.remarks
                    )
                }
            }

            is OtherBankTransferScreenAction.OnReceiversAccountNumberError -> {
                _state.update {
                    it.copy(
                        receiversAccountNumberError = action.receiversAccountNumberError
                    )
                }
            }

            is OtherBankTransferScreenAction.OnReceiversFullNameError -> {
                _state.update {
                    it.copy(
                        receiversFullNameError = action.receiversFullNameError
                    )
                }
            }

            is OtherBankTransferScreenAction.OnAmountError -> {
                _state.update {
                    it.copy(
                        amountError = action.amountError
                    )
                }
            }

            is OtherBankTransferScreenAction.OnRemarksError -> {
                _state.update {
                    it.copy(
                        remarksError = action.remarksError
                    )
                }
            }

            OtherBankTransferScreenAction.OnProceedClicked -> {

            }

            OtherBankTransferScreenAction.OnCancelClicked -> {

            }

        }

    }


}