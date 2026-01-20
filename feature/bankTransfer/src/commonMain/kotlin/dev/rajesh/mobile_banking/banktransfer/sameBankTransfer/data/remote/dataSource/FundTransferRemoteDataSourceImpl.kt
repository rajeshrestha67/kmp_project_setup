package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dataSource

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.AccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.CoopBranchResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.FundTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.Constants
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.post
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.Parameters

class FundTransferRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : FundTransferRemoteDataSource {

    override suspend fun getCoopBranches(): ApiResult<CoopBranchResponseDTO, DataError> {
        return safeCall<CoopBranchResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.COOP_BRANCH
            ){
                headers{
                    append("client", Constants.clientId)
                }
            }
        }
    }

    override suspend fun validateAccount(
        accountValidationRequest: AccountValidationRequest
    ): ApiResult<AccountValidationResponseDTO, DataError> {
        return safeCall<AccountValidationResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.VALIDATE_ACCOUNT
            ) {
                if (accountValidationRequest.destinationAccountNumber != null) {
                    parameter(
                        "destinationAccountNumber",
                        accountValidationRequest.destinationAccountNumber
                    )
                    parameter(
                        "destinationAccountName",
                        accountValidationRequest.destinationAccountName
                    )
                    parameter("destinationBranchId", accountValidationRequest.destinationBranchId)
                } else if (accountValidationRequest.destinationMobileNumber != null) {
                    parameter(
                        "destinationMobileNumber",
                        accountValidationRequest.destinationMobileNumber
                    )
                }

            }
        }
    }

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