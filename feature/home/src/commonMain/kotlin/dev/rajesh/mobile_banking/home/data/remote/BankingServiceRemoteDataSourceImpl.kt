package dev.rajesh.mobile_banking.home.data.remote

import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.Constants
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient

class BankingServiceRemoteDataSourceImpl(
    val httpClient: HttpClient
) : BankingServiceRemoteDataSource {
    override suspend fun fetchBankingService(): ApiResult<BankingServiceDTO, DataError> {
        return safeCall<BankingServiceDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = "${EndPoint.BANKING_SERVICES}/${Constants.clientId}"
            )
        }
    }
}