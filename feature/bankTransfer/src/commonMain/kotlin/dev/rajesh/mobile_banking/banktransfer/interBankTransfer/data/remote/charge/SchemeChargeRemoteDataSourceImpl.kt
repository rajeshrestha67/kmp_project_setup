package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.charge

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.SchemeChargeResponseDTO
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

class SchemeChargeRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : SchemeChargeRemoteDataSource {
    override suspend fun getSchemeCharge(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean
    ): ApiResult<SchemeChargeResponseDTO, DataError> {

        val params = Parameters.build {
            append("amount", amount)
            append("destinationBankId", destinationBankId)
            append("isCoop", isCoop.toString())
        }

        return safeCall<SchemeChargeResponseDTO> {
            httpClient.post(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.SCHEME_CHARGE
            ) {
                setBody(
                    FormDataContent(params)
                )
            }
        }
    }
}