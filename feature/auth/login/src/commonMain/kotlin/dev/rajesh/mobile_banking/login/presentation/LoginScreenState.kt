package dev.rajesh.mobile_banking.login.presentation

import org.jetbrains.compose.resources.StringResource

data class LoginScreenState(
    val mobileNumber: String = "",
    val password: String = "",
    val mobileNumberError: StringResource? = null,
    val passwordError: StringResource? = null,
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
)

