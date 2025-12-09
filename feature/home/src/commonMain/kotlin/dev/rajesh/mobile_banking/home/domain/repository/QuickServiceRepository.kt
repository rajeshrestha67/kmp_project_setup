package dev.rajesh.mobile_banking.home.domain.repository

import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface QuickServiceRepository {

    suspend fun fetchQuickServices(): ApiResult<List<QuickServiceDetail>, DataError>
}