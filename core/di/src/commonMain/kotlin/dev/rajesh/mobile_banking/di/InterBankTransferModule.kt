package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankTransfer.InterBankTransferRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankTransfer.InterBankTransferRemoteDataSourceImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository.InterBankTransferRepositoryImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.InterBankTransferRepository
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.BankAccountValidationUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.FetchSchemeChargeUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.GetBankListUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.InterBankTransferUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel.InterBankTransferViewModel
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel.SelectBankViewModel
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class InterBankTransferModule {

    @Factory
    fun provideBankListUseCase(
        repository: InterBankTransferRepository
    ) = GetBankListUseCase(repository)



    @KoinViewModel
    fun provideViewModel(
        getBankListUseCase: GetBankListUseCase
    ) = SelectBankViewModel(getBankListUseCase)

    @KoinViewModel
    fun provideInterBankTransferViewModel(
        requiredValidationUseCase: RequiredValidationUseCase,
        fetchSchemeChargeUseCase: FetchSchemeChargeUseCase,
        bankAccountValidationUseCase: BankAccountValidationUseCase,
        interBankTransferUseCase: InterBankTransferUseCase,
        selectedAccountStore: SelectedAccountStore
    ) = InterBankTransferViewModel(
        requiredValidationUseCase = requiredValidationUseCase,
        fetchSchemeChargeUseCase = fetchSchemeChargeUseCase,
        bankAccountValidationUseCase = bankAccountValidationUseCase,
        interBankTransferUseCase = interBankTransferUseCase,
        selectedAccountStore = selectedAccountStore
    )

    @Factory
    fun provideBankAccountValidationUseCase(
        repository: InterBankTransferRepository
    ) = BankAccountValidationUseCase(repository)

    @Factory
    fun provideFetchSchemeChargeUseCase(
        repository: InterBankTransferRepository
    ) = FetchSchemeChargeUseCase(repository)


    @Factory(binds = [InterBankTransferRemoteDataSource::class])
    fun provideInterBankTransferRemoteDataSource(
        httpClient: HttpClient
    ) = InterBankTransferRemoteDataSourceImpl(httpClient)

    @Factory(binds = [InterBankTransferRepository::class])
    fun provideInterBankTransferRepository(
        interBankTransferRemoteDataSource: InterBankTransferRemoteDataSource
    ) = InterBankTransferRepositoryImpl(interBankTransferRemoteDataSource)

    @Factory
    fun provideInterBankTransferUseCase(
        interBankTransferRepository: InterBankTransferRepository
    ) = InterBankTransferUseCase(interBankTransferRepository)

}
