package dev.rajesh.mobile_banking.home.data.remote

import dev.rajesh.mobile_banking.home.data.remote.dto.UserDetailResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface RemoteUserDetailDataSource {
    suspend fun fetchUserDetail(): ApiResult<UserDetailResponseDTO, DataError>
}