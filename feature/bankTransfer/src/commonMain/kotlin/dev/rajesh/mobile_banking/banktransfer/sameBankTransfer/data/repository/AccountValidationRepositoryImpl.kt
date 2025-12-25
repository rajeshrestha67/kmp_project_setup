package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.repository

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper.toAccountValidation
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.accountValidation.AccountValidationRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.AccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.AccountValidationRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class AccountValidationRepositoryImpl(
    private val accountValidationRemoteDataSource: AccountValidationRemoteDataSource
) : AccountValidationRepository {
    override suspend fun validateAccount(
        accountValidationRequest: AccountValidationRequest
    ): ApiResult<AccountValidationDetail, DataError> {
        return accountValidationRemoteDataSource
            .validateAccount(accountValidationRequest)
            .map { dto->
                dto.toAccountValidation()
            }
    }

}