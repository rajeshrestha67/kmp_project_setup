package dev.rajesh.mobile_banking.loadWallet.domain.repository

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletChargeResponseDTO
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletLoadDetails
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.presentation.model.WalletLoadRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface WalletRepository {
    suspend fun getWalletList(): ApiResult<List<WalletDetail>, DataError>

    suspend fun validateWallet(
        walletId: String,
        walletUsername: String,
        amount: String,
    ): ApiResult<WalletValidationDetail, DataError>

    suspend fun getWalletCharge(
        amount: String,
        serviceChargeOf: String,
        associatedId: String
    ): ApiResult<WalletChargeDetail, DataError>

    suspend fun walletLoad(
        walletLoadRequest: WalletLoadRequest
    ): ApiResult<WalletLoadDetails, DataError>
}
