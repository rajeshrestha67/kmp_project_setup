package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dataSource

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.AccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.CoopBranchResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.FundTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface FundTransferRemoteDataSource {

    suspend fun getCoopBranches(): ApiResult<CoopBranchResponseDTO, DataError>

    suspend fun validateAccount(
        accountValidationRequest: AccountValidationRequest
    ): ApiResult<AccountValidationResponseDTO, DataError>

    suspend fun fundTransfer(
        fundTransferRequest: FundTransferRequest
    ): ApiResult<FundTransferResponseDTO, DataError>
}