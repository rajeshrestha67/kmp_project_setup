package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.InterBankTransferDetailDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.InterBankTransferDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface InterBankTransferRepository {
    suspend fun interBankTransfer(
        bankTransferRequest: BankTransferRequest
    ): ApiResult<InterBankTransferDetail, DataError>
}