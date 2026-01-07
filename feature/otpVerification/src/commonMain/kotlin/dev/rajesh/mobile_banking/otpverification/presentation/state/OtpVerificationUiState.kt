package dev.rajesh.mobile_banking.otpverification.presentation.state


data class OtpVerificationUiState(
    val otp: String = "",
    val otpLength: Int = 6,
    val isLoading: Boolean = false,
    val error: String? = null,
    val timerSeconds: Int = 60,
    val canResend: Boolean = false,
    val isVerified: Boolean = false

)