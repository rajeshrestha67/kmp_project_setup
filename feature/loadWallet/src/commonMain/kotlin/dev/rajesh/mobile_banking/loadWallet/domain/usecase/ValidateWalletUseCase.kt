package dev.rajesh.mobile_banking.loadWallet.domain.usecase

import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class ValidateWalletUseCase(
    private val walletRepository: WalletRepository
) {
    suspend operator fun invoke(
        walletId: String,
        walletUsername: String,
        amount: String
    ): ApiResult<WalletValidationDetail, DataError> {
        return walletRepository.validateWallet(
            walletId = walletId,
            walletUsername = walletUsername,
            amount = amount
        )
    }
}