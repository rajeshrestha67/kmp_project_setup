package dev.rajesh.mobile_banking.qrscanner.domain.usecases

import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.qrscanner.domain.model.QPayMerchantDetail
import dev.rajesh.mobile_banking.qrscanner.domain.repository.QPayRepository

class GetQPayMerchantDetailUseCase(
    private val repository: QPayRepository
) {
    suspend operator fun invoke(payLoad: String):
            ApiResult<QPayMerchantDetail, DataError> {
        return repository.getQPayMerchantDetail(payLoad)
    }
}