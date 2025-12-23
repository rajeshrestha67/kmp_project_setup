package dev.rajesh.mobile_banking.banktransfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.presentation.state.BankTransferScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class BankTransferScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(BankTransferScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000),
        initialValue = BankTransferScreenState()
    )



}