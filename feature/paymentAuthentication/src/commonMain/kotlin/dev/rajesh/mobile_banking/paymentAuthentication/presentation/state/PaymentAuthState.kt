package dev.rajesh.mobile_banking.paymentAuthentication.presentation.state

data class PaymentAuthState(
    val mPin: String = "",
    val mPinFromDS: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isMpinVisible: Boolean = false,
)