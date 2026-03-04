package dev.rajesh.mobile_banking.aboutus.domain.repository

import dev.rajesh.mobile_banking.aboutus.domain.model.CoopDetail
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlinx.coroutines.flow.Flow

interface CoopDetailRepository {
    suspend fun getCoopDetail(clientId: String): Flow<ApiResult<CoopDetail, DataError>>
}