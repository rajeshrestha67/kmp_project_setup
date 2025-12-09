package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSourceImpl
import dev.rajesh.mobile_banking.home.data.repository.BankingServiceRepositoryImpl
import dev.rajesh.mobile_banking.home.domain.repository.BankingServicesRepository
import dev.rajesh.mobile_banking.home.domain.usecase.FetchBankingServiceUseCase
import dev.rajesh.mobile_banking.home.presentation.HomeScreenViewModel
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class HomeScreenModule {

    @KoinViewModel
    fun homeScreenViewModel(
        fetchUserDetailUseCase: FetchUserDetailUseCase,
        fetchBankingServiceUseCase: FetchBankingServiceUseCase
    ) = HomeScreenViewModel(fetchUserDetailUseCase, fetchBankingServiceUseCase)


    @Factory(binds = [BankingServiceRemoteDataSource::class])
    fun provideBankingServiceRemoteDataSource(
        httpClient: HttpClient
    ) = BankingServiceRemoteDataSourceImpl(httpClient)

    @Factory(binds = [BankingServicesRepository::class])
    fun provideBankingServiceRepository(
        bankingServiceRemoteDataSource: BankingServiceRemoteDataSource
    ) = BankingServiceRepositoryImpl(bankingServiceRemoteDataSource)

    @Factory
    fun fetchBankingServiceUseCase(
        bankingServiceRepository: BankingServicesRepository
    ) = FetchBankingServiceUseCase(bankingServiceRepository)
}