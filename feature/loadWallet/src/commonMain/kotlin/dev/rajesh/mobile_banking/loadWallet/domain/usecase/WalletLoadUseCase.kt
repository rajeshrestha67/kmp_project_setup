package dev.rajesh.mobile_banking.loadWallet.domain.usecase

import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletLoadDetails
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.loadWallet.presentation.model.WalletLoadRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class WalletLoadUseCase(
    val walletRepository: WalletRepository
) {
    suspend operator fun invoke(
        walletLoadRequest: WalletLoadRequest
    ): ApiResult<WalletLoadDetails, DataError> {
        return walletRepository.walletLoad(
            walletLoadRequest = walletLoadRequest
        )
    }
}