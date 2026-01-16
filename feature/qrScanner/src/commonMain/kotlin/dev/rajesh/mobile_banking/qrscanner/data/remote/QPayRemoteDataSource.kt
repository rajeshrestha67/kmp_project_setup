package dev.rajesh.mobile_banking.qrscanner.data.remote

import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.qrscanner.data.dto.QPayMerchantDetailResponseDTO

interface QPayRemoteDataSource {
    suspend fun getQPayMerchantDetails(
        payload: String
    ): ApiResult<QPayMerchantDetailResponseDTO, DataError>
}