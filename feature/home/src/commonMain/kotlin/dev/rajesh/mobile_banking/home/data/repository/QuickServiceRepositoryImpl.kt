package dev.rajesh.mobile_banking.home.data.repository

import dev.rajesh.mobile_banking.home.data.mapper.toQuickServiceDetail
import dev.rajesh.mobile_banking.home.data.remote.QuickServicesRemoteDataSource
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.domain.repository.QuickServiceRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class QuickServiceRepositoryImpl(
    private val quickServiceRemoteDataSource: QuickServicesRemoteDataSource
) : QuickServiceRepository{
    override suspend fun fetchQuickServices(): ApiResult<List<QuickServiceDetail>, DataError> {
        return quickServiceRemoteDataSource
            .getQuickServices()
            .map { dto->
                dto.details.map { it.toQuickServiceDetail()
                }
            }
    }
}