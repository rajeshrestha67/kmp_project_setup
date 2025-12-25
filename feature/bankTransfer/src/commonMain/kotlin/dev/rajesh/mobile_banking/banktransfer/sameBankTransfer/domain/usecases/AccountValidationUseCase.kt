package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.AccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.AccountValidationRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class AccountValidationUseCase(
    private val accountValidationRepository: AccountValidationRepository
) {
    suspend operator fun invoke(accountValidationRequest: AccountValidationRequest)
            : ApiResult<AccountValidationDetail, DataError> {
        return accountValidationRepository.validateAccount(accountValidationRequest)
    }
}