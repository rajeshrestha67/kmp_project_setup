package dev.rajesh.mobile_banking.login.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.components.device_info.getDeviceInfo
import dev.rajesh.mobile_banking.login.domain.usecase.LoginUseCase
import dev.rajesh.mobile_banking.domain.form.MobileNumberValidateUseCase
import dev.rajesh.mobile_banking.domain.form.PasswordValidateUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.login.domain.model.LoginRequest
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.Constants
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.otpverification.presentation.state.OtpEvent
import dev.rajesh.mobile_banking.otpverification.presentation.viewmodel.OtpVerificationViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val mobileNumberValidateUseCase: MobileNumberValidateUseCase,
    private val passwordValidateUseCase: PasswordValidateUseCase
) : ViewModel() {

    private val _errorChannel = Channel<String>()
    val errorChannel = _errorChannel.receiveAsFlow()

    private val _loginEffect = MutableSharedFlow<LoginEffect>()
    val loginEffect: SharedFlow<LoginEffect> = _loginEffect.asSharedFlow()

    val otpVerificationViewModel = OtpVerificationViewModel()


    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.onStart {

    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoginScreenState()
    )

    init {
        observeOtpEvents()
    }

    fun onAction(action: LoginScreenAction) {
        when (action) {
            is LoginScreenAction.OnMobileNumberChanged -> {
                _state.update {
                    it.copy(mobileNumber = action.mobileNumber)
                }
            }

            is LoginScreenAction.OnPasswordChanged -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }

            is LoginScreenAction.OnMobileNumberError -> {
                _state.update {
                    it.copy(mobileNumberError = action.mobileNumberError)
                }
            }

            is LoginScreenAction.OnPasswordError -> {
                _state.update {
                    it.copy(passwordError = action.passwordError)
                }
            }

            LoginScreenAction.OnBiometricLogin -> TODO()
            LoginScreenAction.LoginClicked -> {
                val mobileNumberError = mobileNumberValidateUseCase(state.value.mobileNumber)
                val passwordError = passwordValidateUseCase(state.value.password)
                when {
                    mobileNumberError != null -> {
                        _state.update { it.copy(mobileNumberError = mobileNumberError) }
                    }

                    passwordError != null -> {
                        _state.update { it.copy(passwordError = passwordError) }

                    }

                    else -> login()
                }

            }
        }
    }

    private fun login() = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }

        val username = Constants.clientId + state.value.mobileNumber
        loginUseCase(
            loginRequest = LoginRequest(
                username = username,
                password = state.value.password,
                clientId = Constants.clientId,
                clientSecret = Constants.clientSecret,
                grantType = "password",
                deviceUniqueIdentifier = getDeviceInfo().deviceUniqueIdentifier,
                otp = state.value.otp
            )
        ).onSuccess { data ->
            _state.update {
                it.copy(isLoading = false)
            }
            _loginEffect.emit(LoginEffect.NavigateToDashboard)
//            _loginEffect.emit(LoginEffect.OtpNeeded)
            AppLogger.i(TAG, "login: api response: $data")
        }.onError { error ->
            _state.update {
                it.copy(isLoading = false)
            }
            if (error.toErrorMessage().contains("unauthorized device", ignoreCase = true)) {
                //show otp verification view
                _loginEffect.emit(LoginEffect.OtpNeeded)
            } else {
                _errorChannel.send(error.toErrorMessage())
            }
        }

    }

    private fun observeOtpEvents() {
        viewModelScope.launch {
            otpVerificationViewModel.events.collect { event ->
                when (event) {
                    is OtpEvent.VerifyClicked -> {
                        AppLogger.i("TAG", "otp: ${event.otp}")
                        _state.update {
                            it.copy(otp = event.otp)
                        }
                        login()
                    }

                    OtpEvent.ResendClicked -> {
                        AppLogger.i("TAG", "Resend Otp")

                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}