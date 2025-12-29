package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.AccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases.AccountValidationUseCase
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases.FundTransferUseCase
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.AccountValidationError
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferAction
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferEffect
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferState
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.TransferTab
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationItem
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import dev.rajesh.mobile_banking.utils.serialization.AppJson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SameBankTransferViewModel(
    private val requiredValidationUseCase: RequiredValidationUseCase,
    private val accountValidationUseCase: AccountValidationUseCase,
    private val fundTransferUseCase: FundTransferUseCase,
    private val selectedAccountStore: SelectedAccountStore
) : ViewModel() {

    companion object {
        const val TAG = "SameBankTransferViewModel"
    }

    private val _state = MutableStateFlow(SameBankTransferState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SameBankTransferState()
    )

    fun onTabSelected(tab: TransferTab) {
        _state.update { it.copy(selectedTab = tab) }
    }

//    private val _successChannel = Channel<Boolean>()
//    val successChannel = _successChannel.receiveAsFlow()

    private val _effect =
        MutableSharedFlow<SameBankTransferEffect>(replay = 0, extraBufferCapacity = 1)
    val effect: SharedFlow<SameBankTransferEffect> = _effect.asSharedFlow()

    val selectedAccount: StateFlow<AccountDetail?> =
        selectedAccountStore.selectedAccount


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

            is SameBankTransferAction.OnBranchSelected -> {
                val branch = action.branchJson?.let { json ->
                    AppJson.decodeFromString<CoopBranchDetail>(json)
                }
                _state.update {
                    it.copy(
                        branch = branch
                    )

                }

            }
        }
    }

    private fun proceedForValidation() = viewModelScope.launch {
        val fullNameError = requiredValidationUseCase(state.value.fullName)
        val accountNumberError = requiredValidationUseCase(state.value.accountNumber)
        val mobileNumberError = requiredValidationUseCase(state.value.mobileNumber)
        val amountError = requiredValidationUseCase(state.value.amount)
        val remarksError = requiredValidationUseCase(state.value.remarks)
        val isBranchError = state.value.branch == null

        if (state.value.selectedTab == TransferTab.ACCOUNT) { // Account Number
            when {
                fullNameError != null -> {
                    _state.update {
                        it.copy(
                            fullNameError = fullNameError
                        )
                    }

                }

                accountNumberError != null -> {
                    _state.update {
                        it.copy(
                            accountNumberError = accountNumberError
                        )
                    }
                }

                isBranchError -> {
                    _state.update {
                        it.copy(
                            branchError = SharedRes.Strings.branch
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
                    proceedValidationWithAccountNumber()
                }

            }
        } else { // Mobile Number
            when {
                mobileNumberError != null -> {
                    _state.update {
                        it.copy(
                            mobileNumberError = mobileNumberError
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
                    proceedValidationWithMobileNumber()
                }
            }
        }

    }

    fun proceedValidationWithAccountNumber() = viewModelScope.launch {
        _state.update {
            it.copy(
                validatingAccount = true
            )
        }

        val accountValidationRequest = AccountValidationRequest(
            destinationAccountNumber = state.value.accountNumber,
            destinationAccountName = state.value.fullName,
            destinationBranchId = state.value.branch?.branchCode,
        )

        accountValidationUseCase.invoke(accountValidationRequest).onSuccess { data ->
            _state.update {
                it.copy(validatingAccount = false)
            }
            try {
                if (data.matchPercentage.toInt() >= 100) {
                    //_successChannel.send(true)
                    showConfirmationData(data)
                } else {
                    showValidationError("Account Validation Failed")
                }
            } catch (e: Exception) {
                AppLogger.e(TAG, "Error on validation : ${e.message}")
                showValidationError("Account Validation Failed")
            }
        }.onError { error ->
            AppLogger.e(tag = TAG, "Fetching coop branches failed: ${error.toErrorMessage()}")
            _state.update {
                it.copy(validatingAccount = false)
            }
        }
    }

    fun proceedValidationWithMobileNumber() = viewModelScope.launch {
        _state.update {
            it.copy(
                validatingAccount = true
            )
        }

        val accountValidationRequest = AccountValidationRequest(
            destinationMobileNumber = state.value.mobileNumber
        )

        accountValidationUseCase.invoke(accountValidationRequest)
            .onSuccess { data ->
                _state.update {
                    it.copy(validatingAccount = false)
                }
                try {
                    if (data.matchPercentage.toInt() >= 100) {
                        showConfirmationData(data)
                    } else {
                        showValidationError("Account Validation Failed")
                    }
                } catch (e: Exception) {
                    AppLogger.e(TAG, "Error on validation : ${e.message}")
                    showValidationError("Account Validation Failed")
                }
            }.onError { error ->
                _state.update {
                    it.copy(validatingAccount = false)
                }
                AppLogger.e(tag = TAG, "Fetching coop branches failed: ${error.toErrorMessage()}")
                showValidationError(error.toErrorMessage())
            }
    }


    private fun showConfirmationData(data: AccountValidationDetail) {
        val dataList: MutableList<ConfirmationItem> = mutableListOf()
        if (_state.value.selectedTab == TransferTab.ACCOUNT) {
            dataList.add(ConfirmationItem("Account Number", _state.value.accountNumber))
        } else {
            dataList.add(ConfirmationItem("Mobile Number", _state.value.mobileNumber))
        }
        dataList.add(ConfirmationItem("Receiver's Name", data.destinationAccountName))
        dataList.add(ConfirmationItem("Amount", _state.value.amount))
        dataList.add(ConfirmationItem("Remarks", _state.value.remarks))

        viewModelScope.launch {
            _effect.emit(
                SameBankTransferEffect.NavigateToConfirmation(
                    ConfirmationData(
                        title = "Confirmation",
                        message = data.message,
                        items = dataList
                    )
                )
            )
        }
    }

    private fun showValidationError(message: String) {
        _state.update {
            it.copy(
                accountValidationError = AccountValidationError(
                    title = "Error",
                    message = message
                )
            )
        }
    }

    fun dismissValidationError() {
        _state.update { it.copy(accountValidationError = null) }
    }

    fun onMPinVerified(mPin: String) {
        val account = selectedAccount.value ?: return
        fundTransfer(mPin, accountDetail = account)
    }


    fun fundTransfer(mPin: String, accountDetail: AccountDetail) = viewModelScope.launch {
        val fundTransferRequest = FundTransferRequest(
            fromAccountNumber = accountDetail.accountNumber,
            toAccountNumber = if (_state.value.selectedTab == TransferTab.ACCOUNT) _state.value.accountNumber else null,
            toAccountName = if (_state.value.selectedTab == TransferTab.ACCOUNT) _state.value.fullName else null,
            bankBranchId = if (_state.value.selectedTab == TransferTab.ACCOUNT && _state.value.branch != null) _state.value.branch?.branchCode else null,
            toMobileNumber = if (_state.value.selectedTab == TransferTab.MOBILE) _state.value.mobileNumber else null,
            amount = _state.value.amount,
            remarks = _state.value.remarks,
            mPin = mPin,
        )

        fundTransferUseCase(fundTransferRequest)
            .onSuccess { result ->
                AppLogger.i(TAG, "fund Transfer Success: ${result.message}")
            }.onError { error ->
                AppLogger.i(TAG, "fund Transfer Success: ${error.toErrorMessage()}")
            }
    }

}