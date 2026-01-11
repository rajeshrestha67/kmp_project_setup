package dev.rajesh.mobile_banking.loadWallet.data.remote

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletChargeResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletListResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletLoadResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationResponseDTO
import dev.rajesh.mobile_banking.loadWallet.presentation.model.WalletLoadRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

sealed interface WalletRemoteDataSource {
    suspend fun getWalletList(): ApiResult<WalletListResponseDTO, DataError>

    suspend fun validateWallet(
        walletId: String,
        walletUsername: String,
        amount: String,
    ): ApiResult<WalletValidationResponseDTO, DataError>

    suspend fun getWalletCharge(
        amount: String,
        serviceChargeOf: String,
        associatedId: String
    ): ApiResult<WalletChargeResponseDTO, DataError>

    suspend fun walletLoad(
        walletLoadRequest: WalletLoadRequest
    ): ApiResult<WalletLoadResponseDTO, DataError>
}