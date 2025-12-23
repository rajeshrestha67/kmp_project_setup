package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferState
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.TransferTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SameBankTransferViewModel : ViewModel() {

    private val _state = MutableStateFlow(SameBankTransferState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SameBankTransferState()
    )

    fun onTabSelected(tab: TransferTab) {
        _state.update { it.copy(selectedTab = tab) }
    }
}