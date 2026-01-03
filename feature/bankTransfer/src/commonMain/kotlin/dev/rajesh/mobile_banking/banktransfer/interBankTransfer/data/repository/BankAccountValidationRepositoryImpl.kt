package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper.toAccountValidation
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankAccountValidation.BankAccountValidationRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankAccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankAccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.BankAccountValidationRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class BankAccountValidationRepositoryImpl(
    private val bankAccountValidationRemoteDataSource: BankAccountValidationRemoteDataSource
) : BankAccountValidationRepository {
    override suspend fun validateAccount(
        bankAccountValidationRequest: BankAccountValidationRequest
    ): ApiResult<BankAccountValidationDetail, DataError> {
        return bankAccountValidationRemoteDataSource
            .validateAccount(bankAccountValidationRequest)
            .map { dto ->
                dto.toAccountValidation()
            }
    }

}