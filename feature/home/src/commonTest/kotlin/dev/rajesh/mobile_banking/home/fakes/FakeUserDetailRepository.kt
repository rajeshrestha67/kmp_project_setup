package dev.rajesh.mobile_banking.home.fakes

import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.user.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserDetailRepository : UserDetailRepository {

    val accountDetails: List<AccountDetail> = listOf(
        AccountDetail(
            accountType = "Savings",
            availableBalance = "500",
            actualBalance = "500",
            primary = "true",
            accountNumber = "222",
            accountHolderName = "John Doe",
        )
    )

    var userFromApi: UserDetails = UserDetails(
        firstName = "John",
        lastName = "Doe",
        fullName = "John Doe",
        accountDetail = accountDetails
    )

    //from datastore
    val accountDetailsFromDS: List<AccountDetail> = listOf(
        AccountDetail(
            accountType = "Savings",
            availableBalance = "500",
            actualBalance = "500",
            primary = "true",
            accountNumber = "222",
            accountHolderName = "Will Smith ",
        )
    )

    var userInDS: UserDetails? = UserDetails(
        firstName = "Will",
        lastName = "Smith",
        fullName = "Will Smith",
        accountDetail = accountDetailsFromDS
    )

    val shouldReturnError = false

    //need to implement test cases for data fetch from database
    override suspend fun fetchUserDetail(forceFetch: Boolean): Flow<ApiResult<UserDetails, DataError>> =
        flow {
            if (!shouldReturnError) {
                emit(ApiResult.Success(userFromApi))
            } else {
                emit(ApiResult.Error(DataError.NetworkError.DataUnknown))
            }
        }

    override suspend fun fetchUserDetailFromDS(): UserDetails? {
        return userInDS
    }
}