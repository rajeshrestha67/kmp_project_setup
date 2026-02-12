package dev.rajesh.mobile_banking.home.fakes

import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.repository.BankingServicesRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlin.String

class FakeBankingServiceRepository : BankingServicesRepository {

    var services: List<BankingServiceDetail> = listOf(
        BankingServiceDetail(
            name = "Bank Transfer",
            uniqueIdentifier = "bank_transfer",
            type = "Dashboard",
            status = "Active",
            imageUrl = "some_url",
            appOrder = 1,
            new = false
        ),
        BankingServiceDetail(
            name = "Load Wallet",
            uniqueIdentifier = "load_wallet",
            type = "Dashboard",
            status = "Active",
            imageUrl = "some_url",
            appOrder = 2,
            new = false
        )

    )

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