package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper.toBankList
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.BankListRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.BankListRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class BankListRepositoryImpl(
    private val bankListRemoteDataSource: BankListRemoteDataSource
) : BankListRepository {
    override suspend fun getBankList(): ApiResult<List<BankDetail>, DataError> {
        return bankListRemoteDataSource.getBankList().map { dto ->
            dto.toBankList()
        }
    }
}