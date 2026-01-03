package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankList

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankListResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

sealed interface BankListRemoteDataSource {
    suspend fun getBankList(): ApiResult<BankListResponseDTO, DataError>
}