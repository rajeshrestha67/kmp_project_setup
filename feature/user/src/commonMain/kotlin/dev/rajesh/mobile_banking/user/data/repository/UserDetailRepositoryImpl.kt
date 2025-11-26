package dev.rajesh.mobile_banking.user.data.repository

import dev.rajesh.mobile_banking.user.data.mapper.toUserDetails
import dev.rajesh.mobile_banking.user.data.remote.UserDetailRemoteDataSource
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map
import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.user.domain.repository.UserDetailRepository

class UserDetailRepositoryImpl(
    private val userDetailRemoteDataSource: UserDetailRemoteDataSource,
) : UserDetailRepository {
    override suspend fun fetchUserDetail(): ApiResult<UserDetails, DataError> {
        return userDetailRemoteDataSource
            .fetchUserDetail()
            .map { dto ->
                val user = dto.details.toUserDetails()
                user
            }
    }

    override suspend fun fetchUserDetailFromDB(): UserDetails? {
        TODO("Not yet implemented")
    }


}