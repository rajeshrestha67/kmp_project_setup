package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases.FetchCoopBranchUseCase
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SelectBranchScreenAction
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SelectBranchScreenState
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectBranchScreenViewModel(
    private val fetchCoopBranchUseCase: FetchCoopBranchUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "SelectBranchScreenViewModel"
    }

    private val _state = MutableStateFlow(SelectBranchScreenState())
    val state = _state
        .onStart {
            fetchCoopBranch()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SelectBranchScreenState()
        )

    private val _actions = Channel<SelectBranchScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private fun fetchCoopBranch() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        fetchCoopBranchUseCase().onSuccess { coopBranches ->
            _state.update {
                it.copy(
                    coopBranches = coopBranches
                )
            }

        }.onError { error ->
            AppLogger.e(tag = TAG, "Fetching coop branches failed: ${error.toErrorMessage()}")
        }
    }
}