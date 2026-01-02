package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.BankListRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class GetBankListUseCase(
    private val bankListRepository: BankListRepository
) {
    suspend operator fun invoke(): ApiResult<List<BankDetail>, DataError> {
        return bankListRepository.getBankList()
    }
}