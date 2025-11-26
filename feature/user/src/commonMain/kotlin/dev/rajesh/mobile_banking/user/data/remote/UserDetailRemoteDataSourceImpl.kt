package dev.rajesh.mobile_banking.user.data.remote

import dev.rajesh.mobile_banking.user.data.remote.dto.UserDetailResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import dev.rajesh.mobile_banking.user.data.remote.UserDetailRemoteDataSource
import io.ktor.client.HttpClient

class UserDetailRemoteDataSourceImpl(
    val httpClient: HttpClient
) : UserDetailRemoteDataSource {

    override suspend fun fetchUserDetail(): ApiResult<UserDetailResponseDTO, DataError> {
        return safeCall<UserDetailResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.CUSTOMER_DETAILS
            )
        }
    }
}