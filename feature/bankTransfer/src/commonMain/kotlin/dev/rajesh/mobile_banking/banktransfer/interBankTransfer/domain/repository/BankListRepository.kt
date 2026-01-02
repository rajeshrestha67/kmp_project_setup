package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface BankListRepository {
    suspend fun getBankList(): ApiResult<List<BankDetail>, DataError>
}