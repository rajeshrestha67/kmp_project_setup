package dev.rajesh.mobile_banking.user.domain.repository

import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlinx.coroutines.flow.Flow

interface UserDetailRepository {
    suspend fun fetchUserDetail(forceFetch: Boolean): Flow<ApiResult<UserDetails, DataError>>

    suspend fun fetchUserDetailFromDS(): UserDetails?
}