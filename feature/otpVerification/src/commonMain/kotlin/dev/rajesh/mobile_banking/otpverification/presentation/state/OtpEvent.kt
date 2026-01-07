package dev.rajesh.mobile_banking.otpverification.presentation.state

sealed interface OtpEvent {
    data class VerifyClicked(val otp: String) : OtpEvent
    data object ResendClicked : OtpEvent
}