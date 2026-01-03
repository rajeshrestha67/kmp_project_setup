package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper.toInterBankTransferDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankTransfer.InterBankTransferRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.InterBankTransferDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankTransferRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.InterBankTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class InterBankTransferRepositoryImpl(
    private val interBankTransferRemoteDaaSource: InterBankTransferRemoteDataSource
) : InterBankTransferRepository {
    override suspend fun interBankTransfer(
        bankTransferRequest: BankTransferRequest
    ): ApiResult<InterBankTransferDetail, DataError> {
        return interBankTransferRemoteDaaSource.interBankTransfer(
            bankTransferRequest
        ).map {
            it.toInterBankTransferDetail()
        }
    }
}