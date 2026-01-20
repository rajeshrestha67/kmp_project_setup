package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.AccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.FundTransferDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface FundTransferRepository {

    suspend fun fetchCoopBranches(): ApiResult<List<CoopBranchDetail>, DataError>

    suspend fun validateAccount(
        accountValidationRequest: AccountValidationRequest
    ): ApiResult<AccountValidationDetail, DataError>

    suspend fun fundTransfer(
        fundTransferRequest: FundTransferRequest
    ): ApiResult<FundTransferDetail, DataError>
}