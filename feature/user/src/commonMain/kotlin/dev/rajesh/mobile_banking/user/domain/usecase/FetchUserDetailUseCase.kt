package dev.rajesh.mobile_banking.user.domain.usecase

import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.user.domain.repository.UserDetailRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.onSuccess

class FetchUserDetailUseCase(
    private val userDetailRepository: UserDetailRepository
    //private val userLocalDataSource: UserLocalDataSource
) {

    suspend operator fun invoke(): ApiResult<UserDetails, DataError>{
        return userDetailRepository.fetchUserDetail()
            .onSuccess {
                //save into local database
                //userLocalDataSource.saveUserDetails(userDetails)
            }
    }

}