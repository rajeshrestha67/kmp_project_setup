package dev.rajesh.mobile_banking.loadWallet.data.repository

import dev.rajesh.mobile_banking.loadWallet.data.mapper.toWalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.data.mapper.toWalletList
import dev.rajesh.mobile_banking.loadWallet.data.mapper.toWalletLoadDetail
import dev.rajesh.mobile_banking.loadWallet.data.mapper.toWalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.data.remote.WalletRemoteDataSource
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletLoadDetails
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.loadWallet.presentation.model.WalletLoadRequest
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

    override suspend fun validateWallet(
        walletId: String,
        walletUsername: String,
        amount: String
    ): ApiResult<WalletValidationDetail, DataError> {
        return walletRemoteDataSource.validateWallet(
            walletId = walletId,
            walletUsername = walletUsername,
            amount = amount
        ).map { dto ->
            dto.detail.toWalletValidationDetail()
        }
    }

    override suspend fun getWalletCharge(
        amount: String,
        serviceChargeOf: String,
        associatedId: String
    ): ApiResult<WalletChargeDetail, DataError> {
        return walletRemoteDataSource.getWalletCharge(
            amount = amount,
            serviceChargeOf = serviceChargeOf,
            associatedId = associatedId
        ).map { dto ->
            dto.toWalletChargeDetail()
        }
    }

    override suspend fun walletLoad(walletLoadRequest: WalletLoadRequest)
    : ApiResult<WalletLoadDetails, DataError> {
        return walletRemoteDataSource.walletLoad(walletLoadRequest)
            .map {
                it.toWalletLoadDetail()
            }
    }
}
