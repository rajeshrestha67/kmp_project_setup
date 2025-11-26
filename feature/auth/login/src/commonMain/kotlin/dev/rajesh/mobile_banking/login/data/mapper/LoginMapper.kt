package dev.rajesh.mobile_banking.login.data.mapper

import dev.rajesh.mobile_banking.login.data.dto.LoginResponseDTO
import dev.rajesh.mobile_banking.login.domain.model.LoginData

fun LoginResponseDTO.toData(): LoginData {
    return LoginData(
        access_token = access_token ?: " ",
        expires_in = expires_in ?: 0,
        refresh_token = refresh_token ?: "",
        scope = scope ?: "",
        token_type = token_type ?: ""
    )
}