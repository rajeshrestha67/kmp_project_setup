package dev.rajesh.mobile_banking.user.domain.repository

import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface UserDetailRepository {
    suspend fun fetchUserDetail(): ApiResult<UserDetails, DataError>

    suspend fun fetchUserDetailFromDB(): UserDetails?
}