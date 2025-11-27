package dev.rajesh.mobile_banking.menu.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MenuScreenViewModel(
    private val userDetailUseCase: FetchUserDetailUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "MenuScreenViewModel"
    }

    private val _state = MutableStateFlow(MenuScreenState())
    val state = _state.onStart {
        fetchUserDetails()
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MenuScreenState()
        )


    private fun fetchUserDetails() = viewModelScope.launch {
        userDetailUseCase().onSuccess { userDetails ->
            _state.update {
                it.copy(
                    fullName = userDetails.firstName,
                    lastName = userDetails.lastName
                )
            }

        }.onError { error ->
            AppLogger.e(
                tag = TAG,
                "Fetching user detail failed: ${error.toErrorMessage()}"
            )
        }
    }
}