package dev.rajesh.mobile_banking.home.fakes

import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.user.domain.repository.UserDetailRepository

class FakeUserDetailRepository : UserDetailRepository {

    var userInDS: UserDetails? = null
    val errorResult = ApiResult.Error(DataError.NetworkError.DataUnknown)
    val successResult = ApiResult.Success(UserDetails())

    override suspend fun fetchUserDetail(): ApiResult<UserDetails, DataError> {
        return errorResult
    }

    override suspend fun fetchUserDetailFromDS(): UserDetails? {
        return userInDS
    }
}