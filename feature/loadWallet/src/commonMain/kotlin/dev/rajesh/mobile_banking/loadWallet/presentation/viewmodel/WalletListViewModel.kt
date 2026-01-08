package dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.GetWalletListUseCase
import dev.rajesh.mobile_banking.loadWallet.presentation.state.WalletListScreenState
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletListViewModel(
    private val getWalletListUseCase: GetWalletListUseCase
) : ViewModel() {

    companion object Companion {
        const val TAG = "WalletViewModel"
    }

    private val _state = MutableStateFlow(WalletListScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WalletListScreenState()
    )

    init {
        getWalletList()
    }

    private fun getWalletList() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        getWalletListUseCase.invoke().onSuccess { resultData ->
            _state.update {
                it.copy(
                    walletList = resultData,
                    isLoading = false,
                    errorMessage = null
                )
            }

        }.onError { error ->
            AppLogger.e(tag = TAG, "Fetching wallet list failed: ${error.toErrorMessage()}")
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = error.toErrorMessage()
                )
            }
        }
    }
}