package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankAccountValidation.BankAccountValidationRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankAccountValidation.BankAccountValidationRemoteDataSourceImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankTransfer.InterBankTransferRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankTransfer.InterBankTransferRemoteDataSourceImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.charge.SchemeChargeRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.charge.SchemeChargeRemoteDataSourceImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository.BankAccountValidationRepositoryImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository.InterBankTransferRepositoryImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository.SchemeChargeRepositoryImpl
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.BankAccountValidationRepository
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.InterBankTransferRepository
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.SchemeChargeRepository
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.BankAccountValidationUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.FetchSchemeChargeUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.InterBankTransferUseCase
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel.InterBankTransferViewModel
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class InterBankTransferModule {

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

    @Factory(binds = [BankAccountValidationRemoteDataSource::class])
    fun provideBankAccountValidationRemoteDataSource(
        httpClient: HttpClient
    ) = BankAccountValidationRemoteDataSourceImpl(httpClient)

    @Factory(binds = [BankAccountValidationRepository::class])
    fun provideBankAccountValidationRepository(
        bankAccountValidationRemoteDataSource: BankAccountValidationRemoteDataSource
    ) = BankAccountValidationRepositoryImpl(bankAccountValidationRemoteDataSource)

    @Factory
    fun provideBankAccountValidationUseCase(
        bankAccountValidationRepository: BankAccountValidationRepository
    ) = BankAccountValidationUseCase(bankAccountValidationRepository)


    @Factory(binds = [SchemeChargeRemoteDataSource::class])
    fun provideSchemeChargeRemoteDataSource(
        httpClient: HttpClient
    ) = SchemeChargeRemoteDataSourceImpl(httpClient)

    @Factory(binds = [SchemeChargeRepository::class])
    fun provideSchemeChargeRepository(
        schemeChargeRemoteDataSource: SchemeChargeRemoteDataSource
    ) = SchemeChargeRepositoryImpl(schemeChargeRemoteDataSource)


    @Factory
    fun provideFetchSchemeChargeUseCase(
        schemeChargeRepository: SchemeChargeRepository
    ) = FetchSchemeChargeUseCase(schemeChargeRepository)


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
