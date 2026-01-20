package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.InterBankTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class GetBankListUseCase(
    private val repository: InterBankTransferRepository
) {
    suspend operator fun invoke(): ApiResult<List<BankDetail>, DataError> {
        return repository.getBankList()
    }
}