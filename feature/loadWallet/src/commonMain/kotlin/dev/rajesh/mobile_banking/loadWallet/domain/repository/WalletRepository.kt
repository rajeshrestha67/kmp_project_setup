package dev.rajesh.mobile_banking.loadWallet.domain.repository

import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface WalletRepository {
    suspend fun getWalletList(): ApiResult<List<WalletDetail>, DataError>
}