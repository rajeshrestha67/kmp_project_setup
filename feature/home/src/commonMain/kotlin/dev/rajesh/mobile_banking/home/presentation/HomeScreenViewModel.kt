package dev.rajesh.mobile_banking.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.domain.usecase.FetchBankingServiceUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.FetchQuickServicesUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.GetGreetingUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import dev.rajesh.mobile_banking.utils.DateUtils
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class HomeScreenViewModel(
    private val userDetailUseCase: FetchUserDetailUseCase,
    private val fetchBankingServiceUseCase: FetchBankingServiceUseCase,
    private val fetchQuickServicesUseCase: FetchQuickServicesUseCase,
    private val greetingUseCase: GetGreetingUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "HomeScreenViewModel"
    }

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _actions = Channel<HomeScreenActions>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    init {
        //_state.update { it.copy(greetingMsg = greetingUseCase()) }
        updateGreeting()
        fetchUserDetails(isRefreshing = false)
        fetchBankingService()
        fetchQuickServices()
    }

    @OptIn(ExperimentalTime::class)
    private fun updateGreeting() {
        val greetings = greetingUseCase()
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

        userDetailUseCase(true)
            .onSuccess { userDetail ->
                _state.update {
                    it.copy(
                        fullName = userDetail.fullName,
                        firstName = userDetail.firstName,
                        lastName = userDetail.lastName,
                        isRefreshing = false
                    )
                }
                userDetail.accountDetail.forEach { account ->
                    if (account.primary.equals("true", ignoreCase = true)) {
                        _state.update {
                            it.copy(
                                actualBalance = account.actualBalance,
                                availableBalance = account.availableBalance,
                                accountNumber = account.accountNumber,
                                accountName = account.accountType
                            )
                        }
                    }
                }


            }.onError { error ->
                AppLogger.e(
                    tag = TAG,
                    "Fetching user detail failed: ${error.toErrorMessage()}"
                )
            }

    }

    private fun fetchBankingService() = viewModelScope.launch {
        _state.update {
            it.copy(isBankingServiceLoading = true)
        }

        //delay(5000)
        fetchBankingServiceUseCase().onSuccess { data ->
            AppLogger.i(TAG, "Fetch Banking Services response: $data")
            _state.update {
                it.copy(
                    isBankingServiceLoading = false,
                    bankingServicesList = data
                )
            }

        }.onError { error ->
            AppLogger.i(TAG, "Fetch Banking Services Error: ${error.toErrorMessage()}")
            _state.update {
                it.copy(
                    isBankingServiceLoading = false
                )
            }
        }
    }

    private fun fetchQuickServices() = viewModelScope.launch {
        _state.update {
            it.copy(
                isQuickServiceLoading = true
            )
        }
        fetchQuickServicesUseCase().onSuccess { data ->
            AppLogger.i(TAG, "Fetch Quick Services response: $data")
            _state.update {
                it.copy(
                    isQuickServiceLoading = false,
                    quickServicesList = data
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    isQuickServiceLoading = false
                )
            }
            AppLogger.i(TAG, "Fetch Quick Services Error: ${error.toErrorMessage()}")

        }
    }

    fun onAction(actions: HomeScreenActions) {
        when (actions) {
            is HomeScreenActions.OnBankingServiceClicked -> {
                emit(HomeScreenActions.OnBankingServiceClicked(actions.service))
            }

            is HomeScreenActions.OnQuickServiceClicked -> {
                emit(HomeScreenActions.OnQuickServiceClicked(actions.service))
            }

            HomeScreenActions.OnQrScannerClicked -> {
                emit(HomeScreenActions.OnQrScannerClicked)
            }

            HomeScreenActions.OnNotificationClicked -> {}
            HomeScreenActions.OnSwipeRefresh -> {}
        }
    }

    fun emit(action: HomeScreenActions) {
        viewModelScope.launch {
            _actions.send(action)
        }
    }

}