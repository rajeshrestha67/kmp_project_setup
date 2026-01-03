package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankList.BankListRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankList.BankListRemoteDataSourceImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository.BankListRepositoryImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.BankListRepository
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.GetBankListUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel.SelectBankViewModel
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class BankListModule {

    @Factory(binds = [BankListRemoteDataSource::class])
    fun provideBankListRemoteDataSource(
        httpClient: HttpClient
    ): BankListRemoteDataSource = BankListRemoteDataSourceImpl(
        httpClient = httpClient
    )

    @Factory(binds = [BankListRepository::class])
    fun provideBankListRepository(
        bankListRemoteDataSource: BankListRemoteDataSource
    ): BankListRepository = BankListRepositoryImpl(bankListRemoteDataSource)

    @Factory
    fun provideBankListUseCase(
        bankListRepository: BankListRepository
    ) = GetBankListUseCase(bankListRepository)


    @KoinViewModel
    fun provideViewModel(
        getBankListUseCase: GetBankListUseCase
    ) = SelectBankViewModel(getBankListUseCase)

}