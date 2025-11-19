package dev.rajesh.mobile_banking.repository

import dev.rajesh.mobile_banking.data.userDetail.RemoteUserDetailDataSource
import dev.rajesh.mobile_banking.domain.userDetail.mapper.toUserDetails
import dev.rajesh.mobile_banking.domain.userDetail.model.UserDetails
import dev.rajesh.mobile_banking.domain.userDetail.repository.UserDetailRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class UserDetailRepositoryImpl(
    val remoteUserDetailDataSource: RemoteUserDetailDataSource
) : UserDetailRepository {
    override suspend fun fetchUserDetail(): ApiResult<UserDetails, DataError> {
        return remoteUserDetailDataSource
            .fetchUserDetail()
            .map { dto ->
                dto.details.toUserDetails()
            }
    }
}