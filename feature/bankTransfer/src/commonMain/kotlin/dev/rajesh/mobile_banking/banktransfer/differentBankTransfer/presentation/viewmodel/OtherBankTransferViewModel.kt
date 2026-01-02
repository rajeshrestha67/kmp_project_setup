package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.state.OtherBankTransferScreenAction
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.state.OtherBankTransferScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class OtherBankTransferViewModel() : ViewModel() {

    val _state = MutableStateFlow(OtherBankTransferScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OtherBankTransferScreenState()
    )

    fun onAction(action: OtherBankTransferScreenAction) {

    }


}