package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.data.remote

import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.data.dto.BankListResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult


sealed interface BankListRemoteDataSource {
    suspend fun getBankList(): ApiResult<BankListResponseDTO, DataError>
}