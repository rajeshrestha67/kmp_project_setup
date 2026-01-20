package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dataSource.FundTransferRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dataSource.FundTransferRemoteDataSourceImpl
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.repository.FundTransferRepositoryImpl
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.FundTransferRepository
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases.AccountValidationUseCase
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases.FundTransferUseCase
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SameBankTransferViewModel
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class SameBankTransferModule {

    @Factory
    fun provideRequiredValidationUseCase() = RequiredValidationUseCase()

    @Factory
    fun provideAccountValidationUseCase(
        repository: FundTransferRepository
    ) = AccountValidationUseCase(repository)

    @KoinViewModel
    fun sameBankTransferViewModel(
        requiredValidationUseCase: RequiredValidationUseCase,
        accountValidationUseCase: AccountValidationUseCase,
        fundTransferUseCase: FundTransferUseCase,
        selectedAccountStore: SelectedAccountStore
    ) = SameBankTransferViewModel(
        requiredValidationUseCase = requiredValidationUseCase,
        accountValidationUseCase = accountValidationUseCase,
        fundTransferUseCase = fundTransferUseCase,
        selectedAccountStore = selectedAccountStore
    )


    //fund transfer

    @Factory(binds = [FundTransferRemoteDataSource::class])
    fun provideFundTransferDataSource(
        httpClient: HttpClient
    ) = FundTransferRemoteDataSourceImpl(httpClient)

    @Factory(binds = [FundTransferRepository::class])
    fun provideFundTransferRepository(
        fundTransferRemoteDataSource: FundTransferRemoteDataSource
    ) = FundTransferRepositoryImpl(fundTransferRemoteDataSource)

    @Factory
    fun provideFundTransferUseCase(
        fundTransferRepository: FundTransferRepository
    ) = FundTransferUseCase(fundTransferRepository)


}