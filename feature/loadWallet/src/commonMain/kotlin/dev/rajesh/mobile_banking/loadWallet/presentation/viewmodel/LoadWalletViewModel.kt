package dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.ValidateWalletUseCase
import dev.rajesh.mobile_banking.loadWallet.presentation.state.LoadWalletScreenAction
import dev.rajesh.mobile_banking.loadWallet.presentation.state.LoadWalletScreenState
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoadWalletViewModel(
    private val requiredValidationUseCase: RequiredValidationUseCase,
    private val validateWalletUseCase: ValidateWalletUseCase,
    selectedAccountStore: SelectedAccountStore
) : ViewModel() {

    companion object {
        const val TAG = "LoadWalletViewModel"
    }

    private val _state = MutableStateFlow(LoadWalletScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoadWalletScreenState()
    )

    val selectedAccount: StateFlow<AccountDetail?> = selectedAccountStore.selectedAccount

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

        validateWalletUseCase(
            walletId = _state.value.walletDetail?.id.toString(),
            walletUsername = _state.value.walletId,
            amount = _state.value.amount
        ).onSuccess { resultData ->
            AppLogger.d(TAG, "Wallet validation success: $resultData ")

        }.onError { error ->
            AppLogger.d(TAG, "Wallet validation Error: ${error.toErrorMessage()} ")

        }

    }

}