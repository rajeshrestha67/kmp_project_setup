package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.CoopBranchRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FetchCoopBranchUseCase(
    private val coopBranchRepository: CoopBranchRepository
) {

    suspend operator fun invoke(): ApiResult<List<CoopBranchDetail>, DataError> {
        return coopBranchRepository.fetchCoopBranches()
    }
}