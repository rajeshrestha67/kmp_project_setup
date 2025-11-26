package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.home.data.remote.KtorRemoteUserDetailDataSource
import dev.rajesh.mobile_banking.home.data.remote.RemoteUserDetailDataSource
import dev.rajesh.mobile_banking.user.data.repository.UserDetailRepositoryImpl
import dev.rajesh.mobile_banking.user.domain.repository.UserDetailRepository
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import io.ktor.client.HttpClient
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class UserModule {

    @Factory(binds = [RemoteUserDetailDataSource::class])
    fun getRemoteUserDetailDataSource(httpClient: HttpClient) = KtorRemoteUserDetailDataSource(httpClient)

    @Factory(binds = [UserDetailRepository::class])
    fun getUserDetailRepository(
        remoteUserDetailDataSource: RemoteUserDetailDataSource
    ) = UserDetailRepositoryImpl(remoteUserDetailDataSource)


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