package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.InterBankTransferDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankTransferRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.InterBankTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult


class InterBankTransferUseCase(
    private val repository: InterBankTransferRepository
) {
    suspend operator fun invoke(bankTransferRequest: BankTransferRequest): ApiResult<InterBankTransferDetail, DataError> {
        return repository.interBankTransfer(bankTransferRequest)
    }
}