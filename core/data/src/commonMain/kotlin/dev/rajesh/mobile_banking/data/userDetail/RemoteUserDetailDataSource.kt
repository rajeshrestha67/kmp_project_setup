package dev.rajesh.mobile_banking.data.userDetail

import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.model.userDetail.UserDetailResponseDTO
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface RemoteUserDetailDataSource {
    suspend fun fetchUserDetail(): ApiResult<UserDetailResponseDTO, DataError>
}