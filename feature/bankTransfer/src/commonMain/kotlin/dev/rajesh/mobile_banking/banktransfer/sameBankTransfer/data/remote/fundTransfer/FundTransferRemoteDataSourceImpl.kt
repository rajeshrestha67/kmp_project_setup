package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.fundTransfer

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.FundTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
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

class FundTransferRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : FundTransferRemoteDataSource {
    override suspend fun fundTransfer(
        fundTransferRequest: FundTransferRequest
    ): ApiResult<FundTransferResponseDTO, DataError> {
        val params = Parameters.build {
            append("from_account_number", fundTransferRequest.fromAccountNumber)
            append("amount", fundTransferRequest.amount)
            append("remarks", fundTransferRequest.remarks)
            append("mPin", fundTransferRequest.mPin)
            append("otp", fundTransferRequest.otp.orEmpty())

            if (fundTransferRequest.toMobileNumber != null) {
                append("to_mobile_number", fundTransferRequest.toMobileNumber)
            } else {
                append("to_account_number", fundTransferRequest.toAccountNumber.orEmpty())
                append("destinationAccountName", fundTransferRequest.toAccountName.orEmpty())
                append("bank_branch_id", fundTransferRequest.bankBranchId.orEmpty())
            }
        }
        return safeCall<FundTransferResponseDTO> {
            httpClient.post(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.FUND_TRANSFER
            ) {
                setBody(
                    FormDataContent(params)
                )
            }
        }
    }
}