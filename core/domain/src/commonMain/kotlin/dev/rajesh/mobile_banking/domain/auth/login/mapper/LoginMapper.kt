package dev.rajesh.mobile_banking.domain.auth.login.mapper

import dev.rajesh.mobile_banking.domain.auth.login.model.LoginData
import dev.rajesh.mobile_banking.model.auth.login.LoginResponseDTO

fun LoginResponseDTO.toData(): LoginData {
    return LoginData(
        access_token = access_token ?: " ",
        expires_in = expires_in ?: 0,
        refresh_token = refresh_token ?: "",
        scope = scope ?: "",
        token_type = token_type ?: ""
    )
}