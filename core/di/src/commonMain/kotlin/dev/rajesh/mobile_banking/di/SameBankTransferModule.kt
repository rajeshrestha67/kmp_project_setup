package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.accountValidation.AccountValidationRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.accountValidation.AccountValidationRemoteDataSourceImpl
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.fundTransfer.FundTransferRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.fundTransfer.FundTransferRemoteDataSourceImpl
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.repository.AccountValidationRepositoryImpl
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.repository.FundTransferRepositoryImpl
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.AccountValidationRepository
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.FundTransferRepository
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases.AccountValidationUseCase
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases.FundTransferUseCase
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SameBankTransferViewModel
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class SameBankTransferModule {

    @Factory
    fun provideRequiredValidationUseCase() = RequiredValidationUseCase()

    @Factory(binds = [AccountValidationRemoteDataSource::class])
    fun provideAccountValidationRemoteDataSource(
        httpClient: HttpClient
    ) = AccountValidationRemoteDataSourceImpl(httpClient)

    @Factory(binds = [AccountValidationRepository::class])
    fun provideAccountValidationRepository(
        accountValidationRemoteDataSource: AccountValidationRemoteDataSource
    ) = AccountValidationRepositoryImpl(accountValidationRemoteDataSource)

    @Factory
    fun provideAccountValidationUseCase(
        accountValidationRepository: AccountValidationRepository
    ) = AccountValidationUseCase(accountValidationRepository)

    @KoinViewModel
    fun sameBankTransferViewModel(
        requiredValidationUseCase: RequiredValidationUseCase,
        accountValidationUseCase: AccountValidationUseCase,
        fundTransferUseCase: FundTransferUseCase
    ) = SameBankTransferViewModel(
        requiredValidationUseCase = requiredValidationUseCase,
        accountValidationUseCase = accountValidationUseCase,
        fundTransferUseCase = fundTransferUseCase
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