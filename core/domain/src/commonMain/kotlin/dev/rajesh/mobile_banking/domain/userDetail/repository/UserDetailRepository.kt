package dev.rajesh.mobile_banking.domain.userDetail.repository

import dev.rajesh.mobile_banking.domain.userDetail.model.UserDetails
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface UserDetailRepository {
    suspend fun fetchUserDetail(): ApiResult<UserDetails, DataError>
}