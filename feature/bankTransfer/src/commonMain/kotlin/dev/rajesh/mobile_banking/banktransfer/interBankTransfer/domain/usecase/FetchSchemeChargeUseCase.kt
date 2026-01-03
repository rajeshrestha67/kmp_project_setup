package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.SchemeCharge
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.SchemeChargeRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FetchSchemeChargeUseCase(
    private val schemeChargeRepository: SchemeChargeRepository
) {
    suspend operator fun invoke(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean
    ): ApiResult<SchemeCharge, DataError> {
        return schemeChargeRepository.getSchemeCharge(
            amount = amount,
            destinationBankId = destinationBankId,
            isCoop = isCoop
        )
    }
}