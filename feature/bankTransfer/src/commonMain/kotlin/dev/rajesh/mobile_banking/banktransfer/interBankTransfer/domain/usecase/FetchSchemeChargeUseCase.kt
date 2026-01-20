package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.SchemeCharge
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.InterBankTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FetchSchemeChargeUseCase(
    private val repository: InterBankTransferRepository
) {
    suspend operator fun invoke(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean
    ): ApiResult<SchemeCharge, DataError> {
        return repository.getSchemeCharge(
            amount = amount,
            destinationBankId = destinationBankId,
            isCoop = isCoop
        )
    }
}