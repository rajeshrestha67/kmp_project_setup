package dev.rajesh.mobile_banking.login.model

import org.jetbrains.compose.resources.StringResource

sealed interface LoginScreenAction {
    data class OnMobileNumberChanged(val mobileNumber: String) : LoginScreenAction
    data class OnPasswordChanged(val password: String) : LoginScreenAction
    data class OnMobileNumberError(val mobileNumberError: StringResource?) : LoginScreenAction
    data class OnPasswordError(val passwordError: StringResource?) : LoginScreenAction
    data object LoginClicked : LoginScreenAction
    data object OnBiometricLogin : LoginScreenAction
}