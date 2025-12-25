package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.accountValidation

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.AccountValidationDetailDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.AccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class AccountValidationRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : AccountValidationRemoteDataSource {
    override suspend fun validateAccount(
        accountValidationRequest: AccountValidationRequest
    ): ApiResult<AccountValidationResponseDTO, DataError> {
        return safeCall<AccountValidationResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.VALIDATE_ACCOUNT
            ) {
                if(accountValidationRequest.destinationAccountNumber!= null){
                    parameter("destinationAccountNumber", accountValidationRequest.destinationAccountNumber)
                    parameter("destinationAccountName", accountValidationRequest.destinationAccountName)
                    parameter("destinationBranchId", accountValidationRequest.destinationBranchId)
                }else if(accountValidationRequest.mobileNumber != null){
                    parameter("mobileNumber", accountValidationRequest.mobileNumber)
                }

            }
        }
    }
}