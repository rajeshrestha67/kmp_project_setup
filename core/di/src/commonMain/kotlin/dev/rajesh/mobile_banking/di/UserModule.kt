package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.home.data.remote.UserDetailRemoteDataSourceImpl
import dev.rajesh.mobile_banking.home.data.remote.UserDetailRemoteDataSource
import dev.rajesh.mobile_banking.user.data.repository.UserDetailRepositoryImpl
import dev.rajesh.mobile_banking.user.domain.repository.UserDetailRepository
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import io.ktor.client.HttpClient
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class UserModule {

    @Factory(binds = [UserDetailRemoteDataSource::class])
    fun getRemoteUserDetailDataSource(httpClient: HttpClient) = UserDetailRemoteDataSourceImpl(httpClient)

    @Factory(binds = [UserDetailRepository::class])
    fun getUserDetailRepository(
        userDetailRemoteDataSource: UserDetailRemoteDataSource
    ) = UserDetailRepositoryImpl(userDetailRemoteDataSource)


    @Factory
    fun fetchUserDetailUseCase(
        userDetailRepository: UserDetailRepository
    ) = FetchUserDetailUseCase(userDetailRepository)

    /*@KoinViewModel
    fun homeScreenViewModel(
        fetchUserDetailUseCase: FetchUserDetailUseCase
    ) = HomeScreenViewModel(fetchUserDetailUseCase)
*/
}