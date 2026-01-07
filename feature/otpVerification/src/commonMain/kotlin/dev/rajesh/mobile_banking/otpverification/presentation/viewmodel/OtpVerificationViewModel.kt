package dev.rajesh.mobile_banking.otpverification.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.otpverification.presentation.state.OtpEvent
import dev.rajesh.mobile_banking.otpverification.presentation.state.OtpVerificationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OtpVerificationViewModel(
) : ViewModel() {

    private val _state = MutableStateFlow(OtpVerificationUiState())
    val state: StateFlow<OtpVerificationUiState> = _state

    private val _events = MutableSharedFlow<OtpEvent>(
        replay = 1,
        extraBufferCapacity = 1
    )
    val events: SharedFlow<OtpEvent> = _events

    init {
        startTimer()
    }

    fun onOtpChange(otp: String) {
        _state.value = _state.value.copy(otp = otp)
        if (otp.length <= _state.value.otpLength) {
            _state.value = _state.value.copy(
                otp = otp,
                error = null,
                isVerified = false
            )
        }
    }

    fun verifyOtp() {
        if (_state.value.otp.length < state.value.otpLength) {
            _state.update {
                it.copy(
                    error = "OTP must be ${state.value.otpLength} digits"
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }
            AppLogger.i(TAG, "1.verifyOtp: ${_state.value.otp}")
            _events.emit(OtpEvent.VerifyClicked(_state.value.otp))
        }
    }

    fun resendOtp() {
        _state.update {
            it.copy(
                otp = "",
                error = "",
                canResend = false,
                timerSeconds = 60,
                isVerified = false
            )
        }
        viewModelScope.launch {
            _events.emit(OtpEvent.ResendClicked)
        }
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            for (i in 60 downTo 0) {
                _state.update {
                    it.copy(
                        timerSeconds = i,
                        canResend = i == 0
                    )
                }
                delay(1000)
            }
        }
    }

    companion object {
        const val TAG = "OtpVerificationViewModel"
    }

}