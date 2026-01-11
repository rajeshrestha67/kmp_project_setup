package dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationItem
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletLoadDetails
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.GetWalletServiceChargeUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.ValidateWalletUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.WalletLoadUseCase
import dev.rajesh.mobile_banking.loadWallet.presentation.model.WalletLoadRequest
import dev.rajesh.mobile_banking.loadWallet.presentation.state.LoadWalletScreenAction
import dev.rajesh.mobile_banking.loadWallet.presentation.state.LoadWalletScreenState
import dev.rajesh.mobile_banking.loadWallet.presentation.state.WalletEffect
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoadWalletViewModel(
    private val requiredValidationUseCase: RequiredValidationUseCase,
    private val validateWalletUseCase: ValidateWalletUseCase,
    private val getWalletServiceChargeUseCase: GetWalletServiceChargeUseCase,
    private val walletLoadUseCase: WalletLoadUseCase,
    selectedAccountStore: SelectedAccountStore
) : ViewModel() {

    val TAG = "LoadWalletViewModel"

    private val _state = MutableStateFlow(LoadWalletScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoadWalletScreenState()
    )

    val selectedAccount: StateFlow<AccountDetail?> = selectedAccountStore.selectedAccount

    private val _effect = MutableSharedFlow<WalletEffect>(replay = 0, extraBufferCapacity = 1)
    val effect = _effect.asSharedFlow()

    fun onAction(action: LoadWalletScreenAction) {
        when (action) {

            is LoadWalletScreenAction.OnWalletIdChanged -> {
                _state.update {
                    it.copy(walletId = action.walletId)
                }
            }

            is LoadWalletScreenAction.OnWalletIdError -> {
                _state.update {
                    it.copy(walletIdError = action.walletIdError)
                }
            }


            is LoadWalletScreenAction.OnAmountChanged -> {
                _state.update {
                    it.copy(amount = action.amount)
                }
            }

            is LoadWalletScreenAction.OnAmountError -> {
                _state.update {
                    it.copy(amountError = action.amountError)
                }
            }

            is LoadWalletScreenAction.OnRemarksChanged -> {
                _state.update {
                    it.copy(remarks = action.remarks)
                }
            }

            is LoadWalletScreenAction.OnRemarksError -> {
                _state.update {
                    it.copy(remarksError = action.remarksError)
                }
            }

            is LoadWalletScreenAction.OnProceedClicked -> {
                _state.update {
                    it.copy(walletDetail = action.walletDetail)
                }
                validateWallet()
            }
        }
    }

    private fun validateWallet() = viewModelScope.launch {
        val walletIdError = requiredValidationUseCase(state.value.walletId)
        val amountError = requiredValidationUseCase(state.value.amount)
        val remarksError = requiredValidationUseCase(state.value.remarks)

        val hasError = walletIdError != null || amountError != null || remarksError != null

        if (hasError) {
            _state.update {
                it.copy(
                    walletIdError = walletIdError,
                    amountError = amountError,
                    remarksError = remarksError
                )
            }
        } else {
            proceedForWalletValidation()
        }

    }

    private fun proceedForWalletValidation() = viewModelScope.launch {
        AppLogger.d(TAG, "Account Number: ")
        _state.update {
            it.copy(
                isValidatingWallet = true
            )
        }

        validateWalletUseCase(
            walletId = _state.value.walletDetail?.id.toString(),
            walletUsername = _state.value.walletId,
            amount = _state.value.amount
        ).onSuccess { resultData ->
            AppLogger.d(TAG, "Wallet validation success: $resultData ")
            //fetchCharge
            fetchCharge(resultData)
        }.onError { error ->
            AppLogger.d(TAG, "Wallet validation Error: ${error.toErrorMessage()} ")
            _state.update {
                it.copy(
                    isValidatingWallet = false,
                    walletValidationError = ErrorData(
                        message = error.toErrorMessage()
                    )
                )
            }

        }

    }

    private fun showConfirmationData(walletValidationDetail: WalletValidationDetail) {
        val dataList: MutableList<ConfirmationItem> = mutableListOf()
        val accountDetail = selectedAccount.value ?: return

        dataList.add(ConfirmationItem("Sender's Account Number", accountDetail.accountNumber))
        dataList.add(ConfirmationItem("Wallet Id", _state.value.walletId))
        dataList.add(
            ConfirmationItem(
                "Receiver's Name",
                walletValidationDetail.customerName.orEmpty()
            )
        )
        dataList.add(ConfirmationItem("Amount (NPR)", _state.value.amount))
        dataList.add(ConfirmationItem("Charge (NPR)", _state.value.charge.orEmpty()))
        dataList.add(ConfirmationItem("Remarks", _state.value.remarks))

        viewModelScope.launch {
            _effect.emit(
                WalletEffect.ToConfirmation(
                    confirmationData = ConfirmationData(
                        title = "Confirmation",
                        message = walletValidationDetail.message,
                        items = dataList
                    )
                )
            )
        }
    }

    private fun fetchCharge(walletValidationDetail: WalletValidationDetail) =
        viewModelScope.launch {
            getWalletServiceChargeUseCase(
                amount = _state.value.amount,
                associatedId = _state.value.walletId,
                serviceChargeOf = "SERVICE"
            ).onSuccess { chargeData ->
                AppLogger.i(TAG, "wallet charge Fetch success: ${chargeData}")
                _state.update {
                    it.copy(
                        isValidatingWallet = false,
                        charge = chargeData.details.toString(),
                    )
                }
                showConfirmationData(walletValidationDetail)
            }.onError { error ->
                AppLogger.i(TAG, "wallet charge Fetch error: ${error.toErrorMessage()}")
                _state.update {
                    it.copy(
                        isValidatingWallet = false,
                        walletValidationError = ErrorData(
                            message = error.toErrorMessage()
                        )
                    )
                }
            }
        }


    fun dismissError() {
        _state.update {
            it.copy(
                walletValidationError = null,
                walletTransferError = null
            )
        }
    }

    fun onMPinVerified(mPin: String) {
        val account = selectedAccount.value ?: return
        proceedForWalletTransfer(mPin, account)

    }

    fun proceedForWalletTransfer(mPin: String, account: AccountDetail) = viewModelScope.launch {
        AppLogger.i(TAG, "Proceed for wallet transfer")
        _state.update {
            it.copy(isWalletTransferring = true)
        }
        val walletLoadRequest = WalletLoadRequest(
            senderAccountNumber = account.accountNumber,
            walletId = _state.value.walletDetail?.id.toString(),
            walletUsername = _state.value.walletId,
            amount = _state.value.amount,
            remarks = _state.value.remarks,
            validationIdentifier = _state.value.validationIdentifier.orEmpty()
        )

        walletLoadUseCase(walletLoadRequest).onSuccess { resultData ->
            AppLogger.i(TAG, "Successful wallet transfer: $resultData")
            _state.update {
                it.copy(isWalletTransferring = false)
            }
            navigateToTransactionSuccessScreen(resultData, account)
        }.onError { error ->
            AppLogger.i(TAG, "Error on wallet transfer: ${error.toErrorMessage()}")
            _state.update {
                it.copy(
                    isWalletTransferring = false,
                    walletTransferError = ErrorData(error.toErrorMessage())
                )
            }
        }
    }

    private fun navigateToTransactionSuccessScreen(
        resultData: WalletLoadDetails,
        account: AccountDetail
    ) {
        val dataList: MutableList<TransactionDataItem> = mutableListOf()
        dataList.add(TransactionDataItem("Status", ""))
        dataList.add(TransactionDataItem("TransactionId", resultData.transactionIdentifier))
        dataList.add(TransactionDataItem("Sender's Account Number", account.accountNumber))
        dataList.add(TransactionDataItem("Service to", _state.value.walletDetail?.name.orEmpty()))
        dataList.add(TransactionDataItem("Wallet Id", _state.value.walletId))
        dataList.add(TransactionDataItem("Amount", "NPR.${_state.value.amount}"))
        dataList.add(TransactionDataItem("Charge", "NPR.${_state.value.charge.orEmpty()}"))
        dataList.add(TransactionDataItem("Remarks", _state.value.remarks))

        viewModelScope.launch {
            _effect.emit(
                WalletEffect.ToTransactionSuccessful(
                    transactionData = TransactionData(
                        title = "Transaction Successful",
                        message = resultData.message,
                        items = dataList
                    )
                )
            )
        }
    }

}