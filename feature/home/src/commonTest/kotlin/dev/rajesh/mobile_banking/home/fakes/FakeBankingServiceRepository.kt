package dev.rajesh.mobile_banking.home.fakes

import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.repository.BankingServicesRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlin.String

class FakeBankingServiceRepository : BankingServicesRepository {

    var services = fakeBankingService()

    var shouldReturnError = false
    var fetchCalled = false

    override suspend fun fetchBankingServices(): ApiResult<List<BankingServiceDetail>, DataError> {
        fetchCalled = true
        if (shouldReturnError) {
            return ApiResult.Error(DataError.NetworkError.DataUnknown)
        } else {
            return ApiResult.Success(services)
        }
    }
}