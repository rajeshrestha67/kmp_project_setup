package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.repository

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper.toFundTransferDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.fundTransfer.FundTransferRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.FundTransferDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.FundTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class FundTransferRepositoryImpl(
    private val fundTransferRemoteDataSource: FundTransferRemoteDataSource
) : FundTransferRepository {
    override suspend fun fundTransfer(fundTransferRequest: FundTransferRequest)
            : ApiResult<FundTransferDetail, DataError> {
        return fundTransferRemoteDataSource
            .fundTransfer(fundTransferRequest)
            .map { dto ->
                dto.toFundTransferDetail()
            }
    }
}