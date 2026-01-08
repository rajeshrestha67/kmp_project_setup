package dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.loadWallet.presentation.state.LoadWalletScreenAction
import dev.rajesh.mobile_banking.loadWallet.presentation.state.LoadWalletScreenState
import dev.rajesh.mobile_banking.logger.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoadWalletViewModel(
    private val requiredValidationUseCase: RequiredValidationUseCase,

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

    fun onAction(action: LoadWalletScreenAction) {
        when (action) {
            is LoadWalletScreenAction.OnAccountNumberChanged -> {
                _state.update {
                    it.copy(accountNumber = action.accountNumber)
                }
            }

            is LoadWalletScreenAction.OnAccountNumberError -> {
                _state.update {
                    it.copy(accountNumberError = action.accountNumberError)
                }
            }

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

            LoadWalletScreenAction.OnProceedClicked -> {
                validateWallet()
            }
        }
    }

    private fun validateWallet() = viewModelScope.launch {
        //val accountNumberError = requiredValidationUseCase(state.value.accountNumber)
        val walletIdError = requiredValidationUseCase(state.value.walletId)
        val amountError = requiredValidationUseCase(state.value.amount)
        val remarksError = requiredValidationUseCase(state.value.remarks)

        when {
//            accountNumberError != null -> {
//                _state.update {
//                    it.copy(accountNumberError = accountNumberError)
//                }
//            }

            walletIdError != null -> {
                _state.update {
                    it.copy(walletIdError = walletIdError)
                }
            }

            amountError != null -> {
                _state.update {
                    it.copy(amountError = amountError)
                }
            }

            remarksError != null -> {
                _state.update {
                    it.copy(remarksError = remarksError)
                }
            }

            else -> {
                proceedForWalletValidation()
            }


        }

    }

    private fun proceedForWalletValidation() {
        AppLogger.d(TAG, "Proceed for wallet Validation")

    }

}