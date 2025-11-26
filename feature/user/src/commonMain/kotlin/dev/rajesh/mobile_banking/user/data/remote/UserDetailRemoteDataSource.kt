package dev.rajesh.mobile_banking.user.data.remote

import dev.rajesh.mobile_banking.user.data.remote.dto.UserDetailResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface UserDetailRemoteDataSource {
    suspend fun fetchUserDetail(): ApiResult<UserDetailResponseDTO, DataError>
}