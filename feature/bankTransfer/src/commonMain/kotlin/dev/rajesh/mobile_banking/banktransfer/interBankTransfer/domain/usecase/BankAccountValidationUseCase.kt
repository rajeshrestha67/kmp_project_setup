package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankAccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankAccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.InterBankTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class BankAccountValidationUseCase(
    private val repository: InterBankTransferRepository
) {
    suspend operator fun invoke(bankAccountValidationRequest: BankAccountValidationRequest)
            : ApiResult<BankAccountValidationDetail, DataError> {
        return repository.validateAccount(bankAccountValidationRequest)
    }
}