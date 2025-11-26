package dev.rajesh.mobile_banking.home.presentation

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

class HomeScreenViewModel(
    private val userDetailUseCase: FetchUserDetailUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "HomeScreenViewModel"
    }

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state
        .onStart {
            fetchUserDetails(isRefreshing = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenState()
        )

    private fun fetchUserDetails(isRefreshing: Boolean) = viewModelScope.launch {
        _state.update {
            it.copy(
                isRefreshing = isRefreshing
            )
        }

        userDetailUseCase().onSuccess { userDetail ->
            _state.update {
                it.copy(
                    fullName = userDetail.fullName,
                    lastName = userDetail.lastName
                )
            }
        }.onError { error->
            AppLogger.e(tag = TAG,
                "Fetching user detail failed: ${error.toErrorMessage()}"
                )
        }

    }

}