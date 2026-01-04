package dev.rajesh.mobile_banking.login.data.repository

import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.login.data.dto.LoginRequestDTO
import dev.rajesh.mobile_banking.login.data.dto.LoginResponseDTO
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
        username: String,
        password: String,
        clientId: String,
        clientSecret: String,
        grantType: String,
        deviceIdentifier: String
    ): ApiResult<LoginResponseDTO, DataError> {
        val request = LoginRequestDTO(
            username = username,
            password = password,
            client_id = clientId,
            client_secret = clientSecret,
            grant_type = grantType,
            deviceUniqueIdentifier = deviceIdentifier,
        )

        val params = Parameters.build {
            append("username", username)
            append("password", password)
            append("client_id", clientId)
            append("client_secret", clientSecret)
            append("grant_type", grantType)
            append("deviceUniqueIdentifier", deviceIdentifier)
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