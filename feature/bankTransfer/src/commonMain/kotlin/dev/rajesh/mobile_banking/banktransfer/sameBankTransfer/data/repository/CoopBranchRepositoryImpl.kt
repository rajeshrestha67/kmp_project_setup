package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.repository

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper.toCoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.CoopBranchRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.CoopBranchRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class CoopBranchRepositoryImpl(
    private val coopBranchRemoteDataSource: CoopBranchRemoteDataSource
) : CoopBranchRepository {
    override suspend fun fetchCoopBranches(): ApiResult<List<CoopBranchDetail>, DataError> {
        return coopBranchRemoteDataSource
            .getCoopBranches()
            .map { dto ->
                dto.details.map {
                    it.toCoopBranchDetail()
                }
            }
    }
}