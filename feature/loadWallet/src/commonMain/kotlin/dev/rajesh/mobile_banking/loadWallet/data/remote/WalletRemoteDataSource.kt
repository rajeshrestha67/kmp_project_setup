package dev.rajesh.mobile_banking.loadWallet.data.remote

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletListResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

sealed interface WalletRemoteDataSource {
    suspend fun getWalletList(): ApiResult<WalletListResponseDTO, DataError>
}