package dev.rajesh.mobile_banking.domain.auth.login.usecase

import dev.rajesh.mobile_banking.domain.auth.login.mapper.toData
import dev.rajesh.mobile_banking.domain.auth.login.model.LoginData
import dev.rajesh.mobile_banking.domain.auth.login.repository.UserRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess

class LoginUseCase(
    private val userRepository: UserRepository,
    //private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String,
        grantType: String,
        deviceIdentifier: String,
    ): ApiResult<LoginData, DataError> {
        return userRepository.login(
            username,
            password,
            clientId,
            clientSecret,
            grantType,
            deviceIdentifier,
        ).map {
            it.toData()
        }.onSuccess {

        }.onError {

        }
    }
}