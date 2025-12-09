package dev.rajesh.mobile_banking.home.domain.repository

import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface BankingServicesRepository {

    suspend fun fetchBankingServices(): ApiResult<List<BankingServiceDetail>, DataError>
}