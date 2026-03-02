package dev.rajesh.mobile_banking.user.domain.usecase

import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.user.domain.repository.UserDetailRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FetchUserDetailUseCase(
    private val userDetailRepository: UserDetailRepository
) {

    suspend operator fun invoke(force: Boolean = false): ApiResult<UserDetails, DataError> {
        return userDetailRepository.fetchUserDetail(force)
    }

}