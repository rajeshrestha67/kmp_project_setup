package dev.rajesh.mobile_banking.home.data.repository

import dev.rajesh.mobile_banking.home.data.mapper.toBankingService
import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSource
import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.repository.BankingServicesRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class BankingServiceRepositoryImpl(
    private val bankingServiceRemoteDataSource: BankingServiceRemoteDataSource
) : BankingServicesRepository {
    override suspend fun fetchBankingServices(): ApiResult<List<BankingServiceDetail>, DataError> {
        return bankingServiceRemoteDataSource
            .fetchBankingService()
            .map { bankingServiceDTO ->
                bankingServiceDTO.details
                    ?.map { it.toBankingService() }
                    ?:emptyList()
            }
    }
}