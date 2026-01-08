package dev.rajesh.mobile_banking.loadWallet.data.repository

import dev.rajesh.mobile_banking.loadWallet.data.mapper.toWalletList
import dev.rajesh.mobile_banking.loadWallet.data.remote.WalletRemoteDataSource
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class WalletRepositoryImpl(
    private val walletRemoteDataSource: WalletRemoteDataSource
) : WalletRepository {
    override suspend fun getWalletList(): ApiResult<List<WalletDetail>, DataError> {
        return walletRemoteDataSource.getWalletList()
            .map {
                it.toWalletList()
            }
    }
}
