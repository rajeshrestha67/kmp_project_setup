package dev.rajesh.mobile_banking.loadWallet.fakes

import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletLoadDetails
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.loadWallet.presentation.model.WalletLoadRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FakeWalletRepository : WalletRepository {

    var errorOnWalletFetchWalletList = false
    val errorMessage = "Something went wrong"
    var walletList = fakeWalletList()

    var errorOnValidateWallet = false
    var errorOnServiceCharge = false


    override suspend fun getWalletList(): ApiResult<List<WalletDetail>, DataError> {
        if (errorOnWalletFetchWalletList) {
            return ApiResult.Error(DataError.NetworkError.Custom(errorMessage))
        } else {
            return ApiResult.Success(walletList)
        }
    }

    override suspend fun validateWallet(
        walletId: String,
        walletUsername: String,
        amount: String
    ): ApiResult<WalletValidationDetail, DataError> {
        return if (errorOnValidateWallet) {
            ApiResult.Error(DataError.NetworkError.Custom(errorMessage))
        } else {
            ApiResult.Success(fakeWalletValidationDetail())
        }
    }

    override suspend fun getWalletCharge(
        amount: String,
        serviceChargeOf: String,
        associatedId: String
    ): ApiResult<WalletChargeDetail, DataError> {
        return if (errorOnServiceCharge) {
            ApiResult.Error(DataError.NetworkError.Custom(errorMessage))
        } else {
            ApiResult.Success(fakeWalletChargeDetail())
        }
    }

    override suspend fun walletLoad(walletLoadRequest: WalletLoadRequest): ApiResult<WalletLoadDetails, DataError> {
        TODO("Not yet implemented")
    }
}