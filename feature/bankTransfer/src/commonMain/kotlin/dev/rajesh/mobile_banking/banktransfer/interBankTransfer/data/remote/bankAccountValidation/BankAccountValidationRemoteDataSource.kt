package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankAccountValidation

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankAccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankAccountValidationRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

sealed interface BankAccountValidationRemoteDataSource {
    suspend fun validateAccount(bankAccountValidationRequest: BankAccountValidationRequest): ApiResult<BankAccountValidationResponseDTO, DataError>
}