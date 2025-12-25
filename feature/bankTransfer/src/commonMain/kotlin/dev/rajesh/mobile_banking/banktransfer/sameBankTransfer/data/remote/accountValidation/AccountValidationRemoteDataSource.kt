package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.accountValidation

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.AccountValidationDetailDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.AccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

sealed interface AccountValidationRemoteDataSource {
    suspend fun validateAccount(accountValidationRequest: AccountValidationRequest): ApiResult<AccountValidationResponseDTO, DataError>
}