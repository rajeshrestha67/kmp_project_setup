package dev.rajesh.mobile_banking.data.userDetail

import dev.rajesh.mobile_banking.domain.userDetail.model.UserDetails
import dev.rajesh.mobile_banking.domain.userDetail.repository.UserDetailRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.model.userDetail.UserDetailResponseDTO
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient

class KtorRemoteUserDetailDataSource(
    val httpClient: HttpClient
) : RemoteUserDetailDataSource {

    override suspend fun fetchUserDetail(): ApiResult<UserDetailResponseDTO, DataError> {
        return safeCall<UserDetailResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.CUSTOMER_DETAILS
            )
        }
    }
}