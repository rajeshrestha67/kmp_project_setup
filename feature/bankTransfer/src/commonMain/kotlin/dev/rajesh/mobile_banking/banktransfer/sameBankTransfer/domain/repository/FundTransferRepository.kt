package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.FundTransferDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface FundTransferRepository {
    suspend fun fundTransfer(fundTransferRequest: FundTransferRequest)
            : ApiResult<FundTransferDetail, DataError>
}