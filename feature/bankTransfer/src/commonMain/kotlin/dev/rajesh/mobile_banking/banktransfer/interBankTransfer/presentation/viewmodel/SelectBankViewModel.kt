package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.GetBankListUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.SelectBankAction
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.SelectBankState
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectBankViewModel(
    private val getBankListUseCase: GetBankListUseCase
) : ViewModel() {

    companion object {
        const val TAG = "SelectBankViewModel"
    }


    private val _state = MutableStateFlow(SelectBankState())
    val state = _state.onStart {
        getBankList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SelectBankState()
    )


    private val searchQuery = MutableStateFlow("")

    init {
        searchQuery.debounce(300) //wait 300ms
            .onEach { query ->
                _state.update { state ->
                    state.copy(
                        banks = if (query.isBlank()) {
                            state.allBanks
                        } else {
                            state.allBanks.filter { bank ->
                                bank.bankName.contains(query, ignoreCase = true)
                            }
                        }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun getBankList() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        getBankListUseCase()
            .onSuccess { data ->
                AppLogger.i(TAG, "Bank list fetch Success: $data")
                _state.update {
                    it.copy(
                        allBanks = data,
                        banks = data,
                        isLoading = false
                    )
                }

            }
            .onError { error ->
                AppLogger.i(TAG, "Bank list fetch Success: ${error.toErrorMessage()}")
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.toErrorMessage()
                    )
                }
            }
    }

    fun onAction(action: SelectBankAction) {
        when (action) {
            is SelectBankAction.OnSearchTextChanged -> {
                _state.update {
                    it.copy(searchText = action.searchText)
                }
                searchQuery.value = action.searchText
            }
        }
    }

    fun clearTextFields(){
        _state.update {
            it.copy(searchText = "")
        }
        searchQuery.value = ""
    }
}