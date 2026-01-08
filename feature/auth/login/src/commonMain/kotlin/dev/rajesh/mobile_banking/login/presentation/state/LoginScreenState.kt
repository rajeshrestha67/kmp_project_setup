package dev.rajesh.mobile_banking.login.presentation.state

import org.jetbrains.compose.resources.StringResource

data class LoginScreenState(
    val mobileNumber: String = "9840173991",
    val password: String = "1991",
    val mobileNumberError: StringResource? = null,
    val passwordError: StringResource? = null,
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,

    val otp: String? = null
)

