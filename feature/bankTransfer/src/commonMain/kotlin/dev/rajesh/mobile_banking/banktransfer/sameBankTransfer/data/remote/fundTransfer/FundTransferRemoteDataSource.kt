package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.fundTransfer

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.FundTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface FundTransferRemoteDataSource {
    suspend fun fundTransfer(fundTransferRequest: FundTransferRequest)
            : ApiResult<FundTransferResponseDTO, DataError>
}