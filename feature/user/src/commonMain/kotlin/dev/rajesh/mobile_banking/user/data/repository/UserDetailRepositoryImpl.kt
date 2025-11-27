package dev.rajesh.mobile_banking.user.data.repository

import dev.rajesh.datastore.userData.repository.UserDetailLocalDataSource
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.user.data.mapper.toDomain
import dev.rajesh.mobile_banking.user.data.mapper.toUserDetails
import dev.rajesh.mobile_banking.user.data.mapper.toUserDetailsLocal
import dev.rajesh.mobile_banking.user.data.remote.UserDetailRemoteDataSource
import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.user.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.firstOrNull

class UserDetailRepositoryImpl(
    private val userDetailRemoteDataSource: UserDetailRemoteDataSource,
    private val userDetailLocalDataSource: UserDetailLocalDataSource
) : UserDetailRepository {
    override suspend fun fetchUserDetail(): ApiResult<UserDetails, DataError> {
        return userDetailRemoteDataSource
            .fetchUserDetail()
            .map { dto ->
                val user = dto.details.toUserDetails()
                user
            }.onSuccess { data ->
                userDetailLocalDataSource.saveUserDetails(data.toUserDetailsLocal())
            }
    }

    override suspend fun fetchUserDetailFromDS(): UserDetails? {
        val userDetails = userDetailLocalDataSource.userDetailsLocalFlow.firstOrNull()
        return userDetails?.toDomain()
    }


}