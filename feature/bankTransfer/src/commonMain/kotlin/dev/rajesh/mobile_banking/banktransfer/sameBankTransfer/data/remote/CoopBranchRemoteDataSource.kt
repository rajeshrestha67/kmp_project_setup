package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.CoopBranchResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface CoopBranchRemoteDataSource {

    suspend fun getCoopBranches(): ApiResult<CoopBranchResponseDTO, DataError>
}