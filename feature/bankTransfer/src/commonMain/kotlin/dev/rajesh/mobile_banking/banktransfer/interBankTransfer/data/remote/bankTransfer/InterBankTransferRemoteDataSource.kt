package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankTransfer

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.InterBankTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface InterBankTransferRemoteDataSource {
    suspend fun interBankTransfer(
        bankTransferRequest: BankTransferRequest
    ): ApiResult<InterBankTransferResponseDTO, DataError>
}