package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface CoopBranchRepository {
    suspend fun fetchCoopBranches(): ApiResult<List<CoopBranchDetail>, DataError>
}