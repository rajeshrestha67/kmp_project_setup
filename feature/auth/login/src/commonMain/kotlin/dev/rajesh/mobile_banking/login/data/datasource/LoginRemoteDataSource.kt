package dev.rajesh.mobile_banking.login.data.datasource

import dev.rajesh.mobile_banking.login.data.dto.LoginResponseDTO
import dev.rajesh.mobile_banking.login.domain.model.LoginRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface LoginRemoteDataSource {
    suspend fun login(
        loginRequest: LoginRequest
    ): ApiResult<LoginResponseDTO, DataError>
}