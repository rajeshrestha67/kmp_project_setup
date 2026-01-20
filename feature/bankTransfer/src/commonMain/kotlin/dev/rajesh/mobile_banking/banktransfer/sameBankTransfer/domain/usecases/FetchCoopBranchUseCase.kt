package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.FundTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FetchCoopBranchUseCase(
    private val fundTransferRepository: FundTransferRepository
) {

    suspend operator fun invoke(): ApiResult<List<CoopBranchDetail>, DataError> {
        return fundTransferRepository.fetchCoopBranches()
    }
}