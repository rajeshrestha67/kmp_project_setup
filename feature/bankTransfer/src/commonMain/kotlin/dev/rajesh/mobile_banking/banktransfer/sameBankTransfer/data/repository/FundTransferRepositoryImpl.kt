package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.repository

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper.toAccountValidation
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper.toCoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper.toDomain
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper.toEntity
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper.toFundTransferDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dataSource.FundTransferRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.AccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.FundTransferDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.AccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.request.FundTransferRequest
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.FundTransferRepository
import dev.rajesh.mobile_banking.database.dao.CoopBranchDetailDao
import dev.rajesh.mobile_banking.database.models.coop.CoopBranchDetailEntity
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class FundTransferRepositoryImpl(
    private val fundTransferRemoteDataSource: FundTransferRemoteDataSource,
    private val coopBranchDetailDao: CoopBranchDetailDao
) : FundTransferRepository {

    override suspend fun fetchCoopBranches(): Flow<ApiResult<List<CoopBranchDetail>, DataError>> =
        flow {

            val coopBranchesFromDb: List<CoopBranchDetailEntity>? =
                coopBranchDetailDao.getAllCoopBranchDetails().firstOrNull()
            if (coopBranchesFromDb != null) {
                AppLogger.d(
                    "FundTransferRepositoryImpl",
                    "coopBranchesFromDB : ${coopBranchesFromDb}"
                )
                emit(ApiResult.Success(coopBranchesFromDb.map { it.toDomain() }))
            }

            val apiResult = fundTransferRemoteDataSource.getCoopBranches()
            apiResult.onSuccess { dto ->
                val coopBranchList = dto.details.map { it.toCoopBranchDetail() }
                coopBranchDetailDao.deleteAllCoopBranchDetails()
                coopBranchDetailDao.insertCoopBranchDetail(coopBranchList.map { it.toEntity() })
                emit(ApiResult.Success(coopBranchList))
            }.onError {
                emit(ApiResult.Error(it))
            }
        }

    override suspend fun validateAccount(
        accountValidationRequest: AccountValidationRequest
    ): ApiResult<AccountValidationDetail, DataError> {
        return fundTransferRemoteDataSource
            .validateAccount(accountValidationRequest)
            .map { dto ->
                dto.toAccountValidation()
            }
    }

    override suspend fun fundTransfer(fundTransferRequest: FundTransferRequest)
            : ApiResult<FundTransferDetail, DataError> {
        return fundTransferRemoteDataSource
            .fundTransfer(fundTransferRequest)
            .map { dto ->
                dto.toFundTransferDetail()
            }
    }
}