package dev.rajesh.mobile_banking.loadWallet.data.remote

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletChargeResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletListResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import dev.rajesh.mobile_banking.res.theme.baseline
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

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

    override suspend fun validateWallet(
        walletId: String,
        walletUsername: String,
        amount: String
    ): ApiResult<WalletValidationResponseDTO, DataError> {
        return safeCall<WalletValidationResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.WALLET_VALIDATION
            ) {
                parameter("walletId", walletId)
                parameter("walletUsername", walletUsername)
                parameter("amount", amount)
            }
        }
    }

    override suspend fun getWalletCharge(
        amount: String,
        serviceChargeOf: String,
        associatedId: String
    ): ApiResult<WalletChargeResponseDTO, DataError> {
       return safeCall<WalletChargeResponseDTO> {
           httpClient.get(
               baseUrl = BaseUrl.Url,
               endPoint = EndPoint.WALLET_SERVICE_CHARGE
           ){
               parameter("amount", amount)
               parameter("serviceChargeOf", serviceChargeOf)
               parameter("associatedId", associatedId)
           }
       }
    }
}