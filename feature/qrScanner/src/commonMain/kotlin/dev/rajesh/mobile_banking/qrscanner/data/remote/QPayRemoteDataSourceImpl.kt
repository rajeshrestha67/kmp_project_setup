package dev.rajesh.mobile_banking.qrscanner.data.remote

import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.post
import dev.rajesh.mobile_banking.networkhelper.safeCall
import dev.rajesh.mobile_banking.qrscanner.data.dto.QPayMerchantDetailResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.setBody
import io.ktor.http.Parameters

class QPayRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : QPayRemoteDataSource {
    override suspend fun getQPayMerchantDetails(payload: String): ApiResult<QPayMerchantDetailResponseDTO, DataError> {

        val params = Parameters.build {
            append("payload", payload)
        }

        return safeCall {
            httpClient.post(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.Q_PAY_MERCHANT_DETAIL
            ) {
                setBody(
                    FormDataContent(params)
                )
            }
        }
    }
}