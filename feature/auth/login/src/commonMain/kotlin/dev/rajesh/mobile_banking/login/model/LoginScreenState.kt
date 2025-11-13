package dev.rajesh.mobile_banking.login.model

import org.jetbrains.compose.resources.StringResource

data class LoginScreenState(
    val mobileNumber: String = "9802304437",
    val password: String = "2287",
    val mobileNumberError: StringResource? = null,
    val passwordError: StringResource? = null,
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
)

