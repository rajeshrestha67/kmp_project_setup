package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSourceImpl
import dev.rajesh.mobile_banking.home.data.remote.QuickServicesRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.QuickServicesRemoteDataSourceImpl
import dev.rajesh.mobile_banking.home.data.repository.BankingServiceRepositoryImpl
import dev.rajesh.mobile_banking.home.data.repository.QuickServiceRepositoryImpl
import dev.rajesh.mobile_banking.home.domain.repository.BankingServicesRepository
import dev.rajesh.mobile_banking.home.domain.repository.QuickServiceRepository
import dev.rajesh.mobile_banking.home.domain.usecase.FetchBankingServiceUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.FetchQuickServicesUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.GetGreetingUseCase
import dev.rajesh.mobile_banking.home.presentation.HomeScreenViewModel
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Module
class HomeScreenModule {


    @KoinViewModel
    fun homeScreenViewModel(
        fetchUserDetailUseCase: FetchUserDetailUseCase,
        fetchBankingServiceUseCase: FetchBankingServiceUseCase,
        fetchQuickServicesUseCase: FetchQuickServicesUseCase,
    ) = HomeScreenViewModel(
        fetchUserDetailUseCase,
        fetchBankingServiceUseCase,
        fetchQuickServicesUseCase,
        GetGreetingUseCase(Clock.System)
    )


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


    @Factory(binds = [QuickServicesRemoteDataSource::class])
    fun provideQuickServiceRemoteDataSource(
        httpClient: HttpClient
    ) = QuickServicesRemoteDataSourceImpl(httpClient)

    @Factory(binds = [QuickServiceRepository::class])
    fun provideQuickServiceRepository(
        quickServicesRemoteDataSource: QuickServicesRemoteDataSource
    ) = QuickServiceRepositoryImpl(quickServicesRemoteDataSource)

    @Factory
    fun fetchQuickSupportUseCase(
        quickSupportRepository: QuickServiceRepository
    ) = FetchQuickServicesUseCase(quickSupportRepository)

}