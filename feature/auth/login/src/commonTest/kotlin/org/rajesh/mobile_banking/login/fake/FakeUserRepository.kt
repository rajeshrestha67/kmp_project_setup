package org.rajesh.mobile_banking.login.fake

import dev.rajesh.mobile_banking.login.domain.model.LoginData
import dev.rajesh.mobile_banking.login.domain.model.LoginRequest
import dev.rajesh.mobile_banking.login.domain.repository.UserRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FakeUserRepository : UserRepository {
    var result: ApiResult<LoginData, DataError> =
        ApiResult.Success(
            LoginData(
                access_token = "fake_token",
                expires_in = 123,
                refresh_token = "fake_refresh_token",
                scope = "fake_scope",
                token_type = "fake_token_type"
            )
        )

    override suspend fun login(loginRequest: LoginRequest): ApiResult<LoginData, DataError> {
        return result
    }
}