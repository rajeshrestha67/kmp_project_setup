package dev.rajesh.mobile_banking.loadWallet.domain.usecase

import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class GetWalletListUseCase (
    private val walletRepository: WalletRepository
){
    suspend operator fun invoke(): ApiResult<List<WalletDetail>, DataError>{
        return walletRepository.getWalletList()
    }
}