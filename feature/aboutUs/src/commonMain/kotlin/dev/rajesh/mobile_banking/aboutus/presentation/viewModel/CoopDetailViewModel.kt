package dev.rajesh.mobile_banking.aboutus.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.aboutus.domain.usecase.FetchCoopDetailsUseCase
import dev.rajesh.mobile_banking.aboutus.presentation.state.CoopDetailScreenState
import dev.rajesh.mobile_banking.networkhelper.Constants
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoopDetailViewModel(
    private val fetchCoopDetailsUseCase: FetchCoopDetailsUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "CoopDetailViewModel"
    }

    private val _state = MutableStateFlow(CoopDetailScreenState())
    val state = _state.asStateFlow()

    init {
        fetchCoopDetails()
    }

    private fun fetchCoopDetails() = viewModelScope.launch {
        fetchCoopDetailsUseCase(Constants.clientId).onSuccess { coopDetail ->

        }.onError { error ->

        }

    }
}