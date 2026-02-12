package dev.rajesh.mobile_banking.home.fakes

import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.Service
import dev.rajesh.mobile_banking.home.domain.repository.QuickServiceRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlin.Int

class FakeQuickServiceRepository : QuickServiceRepository {

    var quickServices: List<QuickServiceDetail> = listOf(
        QuickServiceDetail(
            id = 1,
            name = "Top Up",
            imageUrl = "Some Url",
            uniqueIdentifier = "top_up",
            isNew = false,
            appOrder = 1,
            services = emptyList()
        ),
        QuickServiceDetail(
            id = 2,
            name = "NEA",
            imageUrl = "Some Url",
            uniqueIdentifier = "nea",
            isNew = false,
            appOrder = 2,
            services = emptyList()
        ),

        )

    var shouldReturnError = false
    var fetchCalled = false


    override suspend fun fetchQuickServices(): ApiResult<List<QuickServiceDetail>, DataError> {
        fetchCalled = true
        if (shouldReturnError) {
            return ApiResult.Error(DataError.NetworkError.DataUnknown)
        } else {
            return ApiResult.Success(quickServices)
        }
    }
}