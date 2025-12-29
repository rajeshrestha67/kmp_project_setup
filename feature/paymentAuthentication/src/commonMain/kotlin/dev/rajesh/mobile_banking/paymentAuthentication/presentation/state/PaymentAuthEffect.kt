package dev.rajesh.mobile_banking.paymentAuthentication.presentation.state

sealed interface  PaymentAuthEffect {
    data class mPinAuthenticated(val mPin: String): PaymentAuthEffect
}