package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.AccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.FundTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class AccountValidationUseCase(
    private val fundTransferRepository: FundTransferRepository
) {
    suspend operator fun invoke(accountValidationRequest: AccountValidationRequest)
            : ApiResult<AccountValidationDetail, DataError> {
        return fundTransferRepository.validateAccount(accountValidationRequest)
    }
}