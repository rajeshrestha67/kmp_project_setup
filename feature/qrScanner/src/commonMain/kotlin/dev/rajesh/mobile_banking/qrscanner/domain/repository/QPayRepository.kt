package dev.rajesh.mobile_banking.qrscanner.domain.repository

import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.qrscanner.domain.model.QPayMerchantDetail

interface QPayRepository {
    suspend fun getQPayMerchantDetail(payLoad: String): ApiResult<QPayMerchantDetail, DataError>
}