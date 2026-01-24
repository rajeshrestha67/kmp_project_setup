package dev.rajesh.mobile_banking.login.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.components.device_info.DefaultDeviceInfoProvider
import dev.rajesh.mobile_banking.components.device_info.DeviceInfoProvider
import dev.rajesh.mobile_banking.components.device_info.getDeviceInfo
import dev.rajesh.mobile_banking.domain.form.MobileNumberValidateUseCase
import dev.rajesh.mobile_banking.domain.form.PasswordValidateUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.login.domain.model.LoginRequest
import dev.rajesh.mobile_banking.login.domain.usecase.LoginUseCase
import dev.rajesh.mobile_banking.login.presentation.state.LoginScreenAction
import dev.rajesh.mobile_banking.login.presentation.state.LoginScreenState
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.Constants
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.otpverification.presentation.state.OtpEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val mobileNumberValidateUseCase: MobileNumberValidateUseCase,
    private val passwordValidateUseCase: PasswordValidateUseCase,
    private val deviceInfoProvider: DeviceInfoProvider = DefaultDeviceInfoProvider
) : ViewModel() {

    private val _errorChannel = Channel<String>()
    val errorChannel = _errorChannel.receiveAsFlow()

    private val _otpEffect = MutableSharedFlow<OtpEffect>()
    val otpEffect = _otpEffect.asSharedFlow()

    private val _state = MutableStateFlow(LoginScreenState())
    val state : StateFlow<LoginScreenState> = _state

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

    fun login(otp: String? = null) = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }

        val username = Constants.clientId + state.value.mobileNumber
        AppLogger.i(TAG, "Device Info: ${deviceInfoProvider.fetchDeviceInfo()}")
        loginUseCase(
            loginRequest = LoginRequest(
                username = username,
                password = state.value.password,
                clientId = Constants.clientId,
                clientSecret = Constants.clientSecret,
                grantType = "password",
                //deviceUniqueIdentifier = getDeviceInfo().deviceUniqueIdentifier,
                deviceUniqueIdentifier = deviceInfoProvider.fetchDeviceInfo().deviceUniqueIdentifier,
                otp = otp
            )
        ).onSuccess { data ->
            AppLogger.i(TAG, "login: api response: $data")
            _state.update {
                it.copy(isLoading = false)
            }
            _otpEffect.emit(OtpEffect.ActionSuccess)
//            _otpEffect.emit(OtpEffect.NeedsVerification)
        }.onError { error ->
            val errorMsg = error.toErrorMessage()
            _state.update {
                it.copy(isLoading = false)
            }
            when {
                // Scenario 1: Device not authorized (First attempt)
                otp == null && errorMsg.contains("unauthorized", true) -> {
                    _otpEffect.emit(OtpEffect.NeedsVerification)
                }
                // Scenario 2: Wrong OTP (Second attempt while on OTP screen)
                otp != null -> {
                    _otpEffect.emit(OtpEffect.OtpError(errorMsg))
                }
                // Scenario 3: Standard Login failure (Wrong password, etc.)
                else -> {
                    _errorChannel.send(error.toErrorMessage())
                }
            }
        }

    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}