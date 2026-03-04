package dev.rajesh.mobile_banking.aboutus.data.remote

import dev.rajesh.mobile_banking.aboutus.data.dto.CoopDetailResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient

class CoopDetailRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CoopDetailRemoteDataSource {
    override suspend fun getCoopDetails(clientId: String): ApiResult<CoopDetailResponseDTO, DataError> {
        return safeCall<CoopDetailResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.APP_CONTACT_DETAILS + "/${clientId}",
            )
        }
    }
}