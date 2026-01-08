package dev.rajesh.mobile_banking.loadWallet.data.remote

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletListResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient

class WalletRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : WalletRemoteDataSource {
    override suspend fun getWalletList(): ApiResult<WalletListResponseDTO, DataError> {
        return safeCall<WalletListResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.WALLET_LIST
            )
        }
    }
}