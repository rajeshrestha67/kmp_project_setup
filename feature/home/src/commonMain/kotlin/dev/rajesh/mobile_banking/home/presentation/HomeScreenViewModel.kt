package dev.rajesh.mobile_banking.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.home.domain.usecase.FetchBankingServiceUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.FetchQuickServicesUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import dev.rajesh.mobile_banking.utils.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val userDetailUseCase: FetchUserDetailUseCase,
    private val fetchBankingServiceUseCase: FetchBankingServiceUseCase,
    private val fetchQuickServicesUseCase: FetchQuickServicesUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "HomeScreenViewModel"
    }

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state
        .onStart {
            fetchUserDetails(isRefreshing = false)
            fetchBankingService()
            fetchQuickServices()
            updateGreeting()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenState()
        )

    private fun updateGreeting() {
        val greetings = DateUtils.getGreetingForCurrentTime()
        _state.update {
            it.copy(greetingMsg = greetings)
        }
    }

    private fun fetchUserDetails(isRefreshing: Boolean) = viewModelScope.launch {
        _state.update {
            it.copy(
                isRefreshing = isRefreshing
            )
        }

        userDetailUseCase(true).onSuccess { userDetail ->
            _state.update {
                it.copy(
                    fullName = /*userDetail.fullName*/"Rajesh Shrestha",
                    firstName = userDetail.firstName,
                    lastName = userDetail.lastName,
                    accountNumber = "0012595678958",
                    accountName = "Premium Super Talap Khata"
                )
            }
        }.onError { error ->
            AppLogger.e(
                tag = TAG,
                "Fetching user detail failed: ${error.toErrorMessage()}"
            )
        }

    }

    private fun fetchBankingService() = viewModelScope.launch {
        fetchBankingServiceUseCase().onSuccess { data ->
            AppLogger.i(TAG, "Fetch Banking Services response: $data")
        }.onError { error ->
            AppLogger.i(TAG, "Fetch Banking Services Error: ${error.toErrorMessage()}")

        }
    }

    private fun fetchQuickServices() = viewModelScope.launch {
        fetchQuickServicesUseCase().onSuccess {data->
                AppLogger.i(TAG, "Fetch Quick Services response: $data")
        }.onError { error ->
            AppLogger.i(TAG, "Fetch Quick Services Error: ${error.toErrorMessage()}")

        }
    }

}