package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.InterBankTransferScreenAction
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.InterBankTransferScreenState
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InterBankTransferViewModel(
    private val requiredValidationUseCase: RequiredValidationUseCase,

    ) : ViewModel() {

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
                validateAndProceed()
            }

            InterBankTransferScreenAction.OnCancelClicked -> {

            }

        }

    }

    fun validateAndProceed() = viewModelScope.launch {
        val selectedBankError: Boolean = (state.value.selectedBank == null)
        val receiversAccountNumberError =
            requiredValidationUseCase(state.value.receiversAccountNumber)
        val receiversFullNameError = requiredValidationUseCase(state.value.receiversFullName)
        val amountError = requiredValidationUseCase(state.value.amount)
        val remarksError = requiredValidationUseCase(state.value.remarks)

        when {
            selectedBankError -> {
                _state.update {
                    it.copy(

                    )
                }
            }

            receiversAccountNumberError != null -> {
                _state.update {
                    it.copy(
                        receiversAccountNumberError = receiversAccountNumberError
                    )
                }
            }

            receiversFullNameError != null -> {
                _state.update {
                    it.copy(
                        receiversFullNameError = receiversFullNameError
                    )
                }
            }

            amountError != null -> {
                _state.update {
                    it.copy(
                        amountError = amountError
                    )
                }
            }

            remarksError != null -> {
                _state.update {
                    it.copy(
                        remarksError = remarksError
                    )
                }
            }

            else -> {

            }
        }

    }

    fun proceedForValidation() = viewModelScope.launch {


    }


}