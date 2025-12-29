package dev.rajesh.mobile_banking.paymentAuthentication.presentation.state

sealed interface PaymentAuthAction {
    data class OnDigitClick(val digit: Int) : PaymentAuthAction
    data object OnDelete : PaymentAuthAction
    data object OnConfirm : PaymentAuthAction
    data object OnProceedWithBiometric : PaymentAuthAction
    data object OnProceedWithMPin : PaymentAuthAction
    data object ToggleVisibility : PaymentAuthAction

}