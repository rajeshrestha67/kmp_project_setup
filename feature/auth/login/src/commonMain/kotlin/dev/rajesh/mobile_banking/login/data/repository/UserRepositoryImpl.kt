package dev.rajesh.mobile_banking.login.data.repository

import dev.rajesh.mobile_banking.login.data.datasource.LoginRemoteDataSource
import dev.rajesh.mobile_banking.login.data.mapper.toData
import dev.rajesh.mobile_banking.login.domain.model.LoginData
import dev.rajesh.mobile_banking.login.domain.model.LoginRequest
import dev.rajesh.mobile_banking.login.domain.repository.UserRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class UserRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource
) : UserRepository {

    override suspend fun login(
        loginRequest: LoginRequest
    ): ApiResult<LoginData, DataError> {
        return remoteDataSource.login(loginRequest = loginRequest)
            .map {
                it.toData()
            }
    }
}