package dev.rajesh.mobile_banking.login.presentation.state

sealed interface LoginEffect {
    data object OtpNeeded : LoginEffect
    data object NavigateToDashboard : LoginEffect
}