package org.rajesh.mobile_banking.login.fake

import dev.rajesh.mobile_banking.login.domain.model.LoginData
import dev.rajesh.mobile_banking.login.domain.model.LoginRequest
import dev.rajesh.mobile_banking.login.domain.repository.UserRepository
import dev.rajesh.mobile_banking.model.AuthError
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

class FakeUserRepository : UserRepository {
    var lastLoginRequest: LoginRequest? = null
    var shouldReturnUnauthorized: Boolean = false
    var shouldShowWrongOtpError: Boolean = false
    var shouldShowError: Boolean = false

    var result: ApiResult<LoginData, DataError> =
        ApiResult.Success(fakeSuccessResult)

    var unauthorizedResult: ApiResult<LoginData, DataError> =
        //ApiResult.Error(DataError.NetworkError.UnAuthorized)
        ApiResult.Error(DataError.NetworkError.Custom("unauthorized device."))


    override suspend fun login(loginRequest: LoginRequest): ApiResult<LoginData, DataError> {
        lastLoginRequest = loginRequest
        delay(1000)
        if (shouldReturnUnauthorized) {
            return unauthorizedResult
        }else if(shouldShowWrongOtpError){
            return ApiResult.Error(DataError.NetworkError.Custom("Wrong OTP"))
        }else if(shouldShowError){
            return ApiResult.Error(DataError.NetworkError.Custom("some error"))
        }
        return result
    }

    companion object {
        val fakeSuccessResult = LoginData(
            access_token = "fake_token",
            expires_in = 123,
            refresh_token = "fake_refresh_token",
            scope = "fake_scope",
            token_type = "fake_token_type"
        )

    }
}