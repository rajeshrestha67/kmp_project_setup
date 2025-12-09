package dev.rajesh.mobile_banking.home.data.remote

import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServicesResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class QuickServicesRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : QuickServicesRemoteDataSource {
    override suspend fun getQuickServices(): ApiResult<QuickServicesResponseDTO, DataError> {
        return safeCall<QuickServicesResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.QUICK_SERVICES
            ){
                parameter("withService", true)
            }
        }
    }
}