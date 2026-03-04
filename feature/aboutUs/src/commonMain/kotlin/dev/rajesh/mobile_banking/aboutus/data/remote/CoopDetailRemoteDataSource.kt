package dev.rajesh.mobile_banking.aboutus.data.remote

import dev.rajesh.mobile_banking.aboutus.data.dto.CoopDetailResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface CoopDetailRemoteDataSource {
    suspend fun getCoopDetails(clientId: String): ApiResult<CoopDetailResponseDTO, DataError>
}