package dev.rajesh.mobile_banking.login.data.repository

import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.login.data.dto.LoginRequestDTO
import dev.rajesh.mobile_banking.login.data.dto.LoginResponseDTO
import dev.rajesh.mobile_banking.login.domain.model.LoginRequest
import dev.rajesh.mobile_banking.login.domain.repository.UserRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.post
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.setBody
import io.ktor.http.Parameters

class UserRepositoryImpl(
    private val httpclient: HttpClient
) : UserRepository {
    override suspend fun login(
        loginRequest: LoginRequest
    ): ApiResult<LoginResponseDTO, DataError> {
        val params = Parameters.build {
            append("username", loginRequest.username)
            append("password", loginRequest.password)
            append("client_id", loginRequest.clientId)
            append("client_secret", loginRequest.clientSecret)
            append("grant_type", loginRequest.grantType)
            append("deviceUniqueIdentifier", loginRequest.deviceUniqueIdentifier)
            if (loginRequest.otp != null && loginRequest.otp.isNotEmpty()) {
                append("otp", loginRequest.otp.orEmpty())
            }
        }

        return safeCall {
            httpclient.post(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.LOGIN,
            ) {
                //setBody(request)
                setBody(
                    FormDataContent(params)
                )
            }
        }

    }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }

}