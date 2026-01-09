package dev.rajesh.mobile_banking.loadWallet.domain.usecase

import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class GetWalletServiceChargeUseCase(
    private val repository: WalletRepository
) {
    suspend operator fun invoke(
        amount: String,
        serviceChargeOf: String,
        associatedId: String
    ): ApiResult<WalletChargeDetail, DataError> {
        return repository.getWalletCharge(
            amount = amount,
            serviceChargeOf = serviceChargeOf,
            associatedId = associatedId
        )
    }
}