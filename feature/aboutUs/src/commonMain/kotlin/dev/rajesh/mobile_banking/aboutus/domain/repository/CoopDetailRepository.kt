package dev.rajesh.mobile_banking.aboutus.domain.repository

import dev.rajesh.mobile_banking.aboutus.domain.model.CoopDetail
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface CoopDetailRepository{
    suspend fun getCoopDetail(clientId: String): ApiResult<CoopDetail, DataError>
}