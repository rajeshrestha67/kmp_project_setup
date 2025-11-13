package dev.rajesh.mobile_banking.domain.auth.login.repository

import dev.rajesh.mobile_banking.model.auth.login.LoginResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface UserRepository {
    suspend fun login(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String,
        grantType: String,
        deviceIdentifier: String,
    ): ApiResult<LoginResponseDTO, DataError>
}