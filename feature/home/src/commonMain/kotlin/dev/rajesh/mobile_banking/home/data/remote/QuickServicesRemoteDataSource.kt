package dev.rajesh.mobile_banking.home.data.remote

import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServicesResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface QuickServicesRemoteDataSource {
    suspend fun getQuickServices(): ApiResult<QuickServicesResponseDTO, DataError>
}