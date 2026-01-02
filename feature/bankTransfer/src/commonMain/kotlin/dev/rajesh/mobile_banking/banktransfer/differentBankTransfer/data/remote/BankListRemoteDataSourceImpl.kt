package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.data.remote

import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.data.dto.BankListResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient

class BankListRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BankListRemoteDataSource {
    override suspend fun getBankList(): ApiResult<BankListResponseDTO, DataError> {
        return safeCall<BankListResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.BANK_LIST
            )

        }
    }

}