package dev.rajesh.mobile_banking.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.domain.usecase.FetchBankingServiceUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.FetchQuickServicesUseCase
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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _actions = Channel<HomeScreenActions>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

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
                    fullName = "Rajesh Shrestha" ,//userDetail.fullName,
                    firstName = "Rajesh" ,//userDetail.firstName,
                    lastName = "Shrestha" ,//userDetail.lastName,
                )
            }
            userDetail.accountDetail.forEach { account->
                if(account.primary.equals("true",ignoreCase = true)){
                    _state.update {
                        it.copy(
                            actualBalance = "85,00,000",//account.actualBalance,
                            availableBalance = "85,00,000",//account.availableBalance,
                            accountNumber = "SSA00049",//account.accountNumber,
                            accountName = "Premium Super Talap Khata"//account.accountType
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

    fun onBankingServiceClicked(service: BankingServiceDetail) {
        viewModelScope.launch {
            _actions.send(HomeScreenActions.OnBankingServiceClicked(service))
        }
    }

    fun onQuickServiceClicked(service: QuickServiceDetail) {
        when (service.uniqueIdentifier) {
            "bank_transfer" -> {

            }

            "fund_transfer" -> {

            }

            else -> {

            }
        }
    }

}