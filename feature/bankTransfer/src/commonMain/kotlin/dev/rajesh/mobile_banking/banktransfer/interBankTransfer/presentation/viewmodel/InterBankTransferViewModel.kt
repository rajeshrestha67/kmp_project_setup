package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankAccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.InterBankTransferDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.SchemeCharge
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankAccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankTransferRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.BankAccountValidationUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.FetchSchemeChargeUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.InterBankTransferUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.InterBankTransferEffect
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.InterBankTransferScreenAction
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.InterBankTransferScreenState
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferEffect
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationItem
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.ErrorData
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionData
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionDataItem
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InterBankTransferViewModel(
    private val requiredValidationUseCase: RequiredValidationUseCase,
    private val fetchSchemeChargeUseCase: FetchSchemeChargeUseCase,
    private val bankAccountValidationUseCase: BankAccountValidationUseCase,
    private val interBankTransferUseCase: InterBankTransferUseCase,
    selectedAccountStore: SelectedAccountStore
) : ViewModel() {

    companion object {
        const val TAG = "InterBankTransferViewModel"
    }

    val _state = MutableStateFlow(InterBankTransferScreenState())
    val state = _state.onStart {}
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InterBankTransferScreenState()
        )

    private val _effect =
        MutableSharedFlow<InterBankTransferEffect>(replay = 0, extraBufferCapacity = 1)
    val effect = _effect.asSharedFlow()

    val selectedAccount: StateFlow<AccountDetail?> = selectedAccountStore.selectedAccount

    fun onAction(action: InterBankTransferScreenAction) {
        when (action) {
            is InterBankTransferScreenAction.OnBankSelected -> {
                _state.update {
                    AppLogger.i(TAG, "selectedBank Bank: ${action.selectedBank}")
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

            is InterBankTransferScreenAction.InitFromNavigation -> {
                _state.update {
                    it.copy(
                        receiversAccountNumber = action.accountNumber.orEmpty(),
                        receiversFullName = action.accountName.orEmpty(),
                        selectedBank = action.bank
                    )
                }
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

        val hasError = selectedBankError
                || receiversAccountNumberError != null
                || receiversFullNameError != null
                || amountError != null
                || remarksError != null

        if (hasError) {
            _state.update {
                it.copy(
                    receiversAccountNumberError = receiversAccountNumberError,
                    receiversFullNameError = receiversFullNameError,
                    amountError = amountError,
                    remarksError = remarksError,
                )
            }
        } else {
            proceedForValidation()
        }
    }

    fun proceedForValidation() = viewModelScope.launch {
        val bankAccountValidationRequest = BankAccountValidationRequest(
            destinationAccountNumber = state.value.receiversAccountNumber,
            destinationAccountName = state.value.receiversFullName,
            destinationBankId = state.value.selectedBank?.bankId,
        )
        _state.update {
            it.copy(
                isValidatingAccount = true
            )
        }
        bankAccountValidationUseCase(
            bankAccountValidationRequest = bankAccountValidationRequest
        ).onSuccess { data ->
            AppLogger.i(TAG, "Bank account validation success: $data")
            fetchSchemeCharge(data)
        }.onError { error ->
            AppLogger.i(TAG, "Bank account validation Error: ${error.toErrorMessage()}")
            _state.update {
                it.copy(
                    isValidatingAccount = false,
                    errorData = ErrorData(error.toErrorMessage())
                )
            }
        }
    }

    fun fetchSchemeCharge(bankAccountValidationDetail: BankAccountValidationDetail) =
        viewModelScope.launch {
            fetchSchemeChargeUseCase(
                amount = state.value.amount,
                destinationBankId = state.value.selectedBank?.bankId.orEmpty(),
                isCoop = false
            ).onSuccess { result ->
                AppLogger.i(TAG, "scheme charge Fetch success: $result")
                _state.update {
                    it.copy(
                        isValidatingAccount = false,
                        charge = result.details.toString()
                    )
                }
                //navigate to confirmation screen
                showConfirmationData(result, bankAccountValidationDetail)

            }.onError { error ->
                AppLogger.i(TAG, "scheme charge Fetch Error: ${error.toErrorMessage()}")
                _state.update {
                    it.copy(
                        isValidatingAccount = false,
                        errorData = ErrorData(error.toErrorMessage())
                    )
                }
            }
        }

    private fun showConfirmationData(
        schemeCharge: SchemeCharge,
        data: BankAccountValidationDetail
    ) {
        val dataList: MutableList<ConfirmationItem> = mutableListOf()
        dataList.add(
            ConfirmationItem(
                "Sender's Account Number",
                selectedAccount.value?.accountNumber.orEmpty()
            )
        )
        dataList.add(
            ConfirmationItem(
                "Receiver's Account Number",
                _state.value.receiversAccountNumber
            )
        )
        dataList.add(ConfirmationItem("Receiver's Name", _state.value.receiversFullName))
        dataList.add(ConfirmationItem("Bank", _state.value.selectedBank?.bankName.orEmpty()))
        dataList.add(ConfirmationItem("Amount", _state.value.amount))
        dataList.add(ConfirmationItem("Remarks", _state.value.remarks))
        dataList.add(ConfirmationItem("Charge", schemeCharge.details.toString()))

        viewModelScope.launch {
            _effect.emit(
                InterBankTransferEffect.NavigateToConfirmation(
                    confirmationData = ConfirmationData(
                        title = "Confirmation",
                        message = data.message,
                        items = dataList
                    )
                )
            )
        }
    }

    fun dismissError() {
        _state.update { it.copy(errorData = null) }
    }

    fun onMPinVerified(mPin: String) {
        val account = selectedAccount.value ?: return
        proceedForBankTransfer(mPin, account)
    }

    private fun proceedForBankTransfer(mPin: String, account: AccountDetail) =
        viewModelScope.launch {
            AppLogger.i(TAG, "Proceed for bank transfer")

            val bankTransferRequest = BankTransferRequest(
                sendersAccountNumber = account.accountNumber,
                destinationBankId = _state.value.selectedBank?.bankId.orEmpty(),
                destinationBankName = _state.value.selectedBank?.bankName.orEmpty(),
                receiversAccountNumber = _state.value.receiversAccountNumber,
                receiversFullName = _state.value.receiversFullName,
                amount = _state.value.amount,
                charge = _state.value.charge.orEmpty(),
                remarks = _state.value.remarks,
                mPin = mPin
            )

            _state.update {
                it.copy(
                    isTransferringFund = true
                )
            }

            interBankTransferUseCase(bankTransferRequest)
                .onSuccess { result ->
                    AppLogger.i(TAG, "inter bank transfer success: ${result}")
                    _state.update {
                        it.copy(
                            isTransferringFund = false
                        )
                    }
                    navigateToTransactionSuccessScreen(result, account)
                }
                .onError { error ->
                    AppLogger.i(TAG, "inter bank transfer error: ${error.toErrorMessage()}")
                    _state.update {
                        it.copy(
                            isTransferringFund = false,
                            errorData = ErrorData(
                                error.toErrorMessage()
                            )
                        )
                    }
                }

        }

    private fun navigateToTransactionSuccessScreen(
        interBankTransferDetail: InterBankTransferDetail,
        account: AccountDetail
    ) {
        val dataList: MutableList<TransactionDataItem> = mutableListOf()

        dataList.add(TransactionDataItem("Status", interBankTransferDetail.status))
        dataList.add(
            TransactionDataItem(
                "TransactionId",
                interBankTransferDetail.transactionIdentifier
            )
        )

        dataList.add(TransactionDataItem("Sender's Account Number", account.accountNumber))
        dataList.add(TransactionDataItem("Sender's Name", account.accountHolderName))

        dataList.add(
            TransactionDataItem(
                "Receiver's Account Number",
                _state.value.receiversAccountNumber
            )
        )
        dataList.add(TransactionDataItem("Receiver's Name", _state.value.receiversFullName))
        dataList.add(TransactionDataItem("Bank", _state.value.selectedBank?.bankName.orEmpty()))
        dataList.add(TransactionDataItem("Amount(NPR.)", _state.value.amount))
        dataList.add(TransactionDataItem("Charge(NPR.)", _state.value.charge.orEmpty()))
        dataList.add(TransactionDataItem("Remarks", _state.value.remarks))

        viewModelScope.launch {
            _effect.emit(
                InterBankTransferEffect.TransactionSuccessful(
                    TransactionData(
                        title = "Transaction Successful",
                        message = interBankTransferDetail.message,
                        items = dataList
                    )
                )
            )
        }

    }

}