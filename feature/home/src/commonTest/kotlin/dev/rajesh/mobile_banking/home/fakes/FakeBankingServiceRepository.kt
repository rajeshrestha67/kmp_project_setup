package dev.rajesh.mobile_banking.home.fakes

import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.repository.BankingServicesRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FakeBankingServiceRepository : BankingServicesRepository {
    override suspend fun fetchBankingServices(): ApiResult<List<BankingServiceDetail>, DataError> {
        return ApiResult.Success(emptyList())
    }
}