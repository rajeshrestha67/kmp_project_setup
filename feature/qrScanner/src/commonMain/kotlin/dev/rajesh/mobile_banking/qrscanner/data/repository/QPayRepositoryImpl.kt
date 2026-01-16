package dev.rajesh.mobile_banking.qrscanner.data.repository

import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map
import dev.rajesh.mobile_banking.qrscanner.data.mapper.toQPayMerchantDetail
import dev.rajesh.mobile_banking.qrscanner.data.remote.QPayRemoteDataSource
import dev.rajesh.mobile_banking.qrscanner.domain.model.QPayMerchantDetail
import dev.rajesh.mobile_banking.qrscanner.domain.repository.QPayRepository

class QPayRepositoryImpl(
    private val remoteDataSource: QPayRemoteDataSource
) : QPayRepository {
    override suspend fun getQPayMerchantDetail(payLoad: String)
            : ApiResult<QPayMerchantDetail, DataError> {
        return remoteDataSource.getQPayMerchantDetails(payLoad)
            .map { dto ->
                dto.toQPayMerchantDetail()
            }
    }
}