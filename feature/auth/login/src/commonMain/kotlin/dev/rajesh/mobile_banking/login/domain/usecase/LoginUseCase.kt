package dev.rajesh.mobile_banking.login.domain.usecase

import dev.rajesh.datastore.token.model.Token
import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.login.data.mapper.toData
import dev.rajesh.mobile_banking.login.domain.model.LoginData
import dev.rajesh.mobile_banking.login.domain.model.LoginRequest
import dev.rajesh.mobile_banking.login.domain.repository.UserRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import kotlinx.coroutines.flow.firstOrNull

class LoginUseCase(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(
        loginRequest: LoginRequest
    ): ApiResult<LoginData, DataError> {
        return userRepository.login(
            loginRequest
        ).onSuccess { data ->
            val token = tokenRepository.token.firstOrNull() ?: Token()
            tokenRepository.saveToken(
                token.copy(
                    jwtToken = data.access_token,
                    mPin = loginRequest.password
                )
            )
        }
    }
}