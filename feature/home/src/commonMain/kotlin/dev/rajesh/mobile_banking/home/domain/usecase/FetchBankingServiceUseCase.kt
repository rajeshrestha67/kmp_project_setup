package dev.rajesh.mobile_banking.home.domain.usecase

import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.repository.BankingServicesRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class FetchBankingServiceUseCase(
    private val repository: BankingServicesRepository
) {
    suspend operator fun invoke(): ApiResult<List<BankingServiceDetail>, DataError> {
        return repository.fetchBankingServices().map { list ->
            list.filter {
                it.status.equals("active", ignoreCase = true)
                        && it.type.equals("dashboard", ignoreCase = true)
            }.sortedBy {
                it.appOrder
            }
        }
    }
}