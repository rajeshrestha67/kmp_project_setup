package org.rajesh.mobile_banking.login.fake

import dev.rajesh.mobile_banking.login.data.datasource.LoginRemoteDataSource
import dev.rajesh.mobile_banking.login.data.dto.LoginResponseDTO
import dev.rajesh.mobile_banking.login.domain.model.LoginRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FakeLoginRemoteDataSource : LoginRemoteDataSource {

    var result: ApiResult<LoginResponseDTO, DataError> =
        ApiResult.Success(
            LoginResponseDTO(
                access_token = "fake_token",
                expires_in = 123,
                refresh_token = "fake_refresh_token",
                scope = "fake_scope",
                token_type = "fake_token_type"
            )
        )

    override suspend fun login(loginRequest: LoginRequest): ApiResult<LoginResponseDTO, DataError> {
        return result
    }
}