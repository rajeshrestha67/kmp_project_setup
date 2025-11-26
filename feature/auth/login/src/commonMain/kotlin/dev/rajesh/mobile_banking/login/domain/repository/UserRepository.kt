package dev.rajesh.mobile_banking.login.domain.repository

import dev.rajesh.mobile_banking.login.data.dto.LoginResponseDTO
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