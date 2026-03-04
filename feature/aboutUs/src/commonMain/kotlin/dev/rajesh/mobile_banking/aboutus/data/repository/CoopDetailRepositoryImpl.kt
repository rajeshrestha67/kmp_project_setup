package dev.rajesh.mobile_banking.aboutus.data.repository

import dev.rajesh.mobile_banking.aboutus.data.mapper.toCoopDetail
import dev.rajesh.mobile_banking.aboutus.data.mapper.toDomain
import dev.rajesh.mobile_banking.aboutus.data.mapper.toEntity
import dev.rajesh.mobile_banking.aboutus.data.remote.CoopDetailRemoteDataSource
import dev.rajesh.mobile_banking.aboutus.domain.model.CoopDetail
import dev.rajesh.mobile_banking.aboutus.domain.repository.CoopDetailRepository
import dev.rajesh.mobile_banking.database.dao.CoopDetailDao
import dev.rajesh.mobile_banking.database.models.coop.CoopDetailEntity
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import kotlinx.coroutines.flow.firstOrNull

class CoopDetailRepositoryImpl(
    private val coopDetailRemoteDataSource: CoopDetailRemoteDataSource,
    private val coopDetailsDao: CoopDetailDao
) : CoopDetailRepository {
    override suspend fun getCoopDetail(
        clientId: String): ApiResult<CoopDetail, DataError> {

        val coopDetailFromDB: CoopDetailEntity? =
            coopDetailsDao.getCoopDetailByClientId(clientId).firstOrNull()

        if (coopDetailFromDB != null) {
            AppLogger.d(
                "CoopDetailRepositoryImpl",
                "getCoopDetail: coopDetailFromDB = $coopDetailFromDB"
            )
            return ApiResult.Success(coopDetailFromDB.toDomain())
        }

        return coopDetailRemoteDataSource.getCoopDetails(clientId)
            .map { coopDetailResponseDTO ->
                coopDetailResponseDTO.toCoopDetail()
            }.onSuccess { data ->
                coopDetailsDao.deleteCoopById(clientId)
                coopDetailsDao.saveCoopDetail(data.toEntity(clientId))
            }
    }
}