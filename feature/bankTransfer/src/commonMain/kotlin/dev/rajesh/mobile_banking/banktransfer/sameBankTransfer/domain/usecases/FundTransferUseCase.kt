package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.FundTransferDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.FundTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FundTransferUseCase(
    private val fundTransferRepository: FundTransferRepository
) {
    suspend operator fun invoke(fundTransferRequest: FundTransferRequest)
            : ApiResult<FundTransferDetail, DataError> {
        return fundTransferRepository.fundTransfer(fundTransferRequest)
    }
}