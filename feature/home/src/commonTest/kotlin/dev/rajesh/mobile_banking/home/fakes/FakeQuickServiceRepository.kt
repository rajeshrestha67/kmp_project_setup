package dev.rajesh.mobile_banking.home.fakes

import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.domain.repository.QuickServiceRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FakeQuickServiceRepository: QuickServiceRepository {
    override suspend fun fetchQuickServices(): ApiResult<List<QuickServiceDetail>, DataError> {
        return ApiResult.Success(emptyList())
    }
}