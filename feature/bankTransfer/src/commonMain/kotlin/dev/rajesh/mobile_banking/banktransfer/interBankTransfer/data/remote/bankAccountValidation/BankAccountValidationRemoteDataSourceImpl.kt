package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankAccountValidation

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankAccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankAccountValidationRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class BankAccountValidationRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BankAccountValidationRemoteDataSource {
    override suspend fun validateAccount(
        bankAccountValidationRequest: BankAccountValidationRequest
    ): ApiResult<BankAccountValidationResponseDTO, DataError> {
        return safeCall<BankAccountValidationResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.INTER_BANK_VALIDATION
            ) {
                parameter(
                    "destinationAccountNumber",
                    bankAccountValidationRequest.destinationAccountNumber
                )
                parameter(
                    "destinationAccountName",
                    bankAccountValidationRequest.destinationAccountName
                )
                parameter("destinationBankId", bankAccountValidationRequest.destinationBankId)
            }
        }
    }
}