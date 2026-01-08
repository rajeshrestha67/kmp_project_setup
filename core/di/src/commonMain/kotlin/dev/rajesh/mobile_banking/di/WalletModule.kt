package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.loadWallet.data.remote.WalletRemoteDataSource
import dev.rajesh.mobile_banking.loadWallet.data.remote.WalletRemoteDataSourceImpl
import dev.rajesh.mobile_banking.loadWallet.data.repository.WalletRepositoryImpl
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.GetWalletListUseCase
import dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel.LoadWalletViewModel
import dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel.WalletListViewModel
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class WalletModule {

    @Factory(binds = [WalletRemoteDataSource::class])
    fun provideWalletRemoteDataSource(httpClient: HttpClient) =
        WalletRemoteDataSourceImpl(httpClient)

    @Factory(binds = [WalletRepository::class])
    fun provideWalletDetailRepository(
        walletRemoteDataSource: WalletRemoteDataSource
    ) = WalletRepositoryImpl(
        walletRemoteDataSource = walletRemoteDataSource
    )

    @Factory
    fun provideWalletDetailUseCase(
        walletRepository: WalletRepository
    ) = GetWalletListUseCase(walletRepository)

    @KoinViewModel
    fun provideWalletListViewModel(
        getWalletListUseCase: GetWalletListUseCase
    ) = WalletListViewModel(
        getWalletListUseCase = getWalletListUseCase
    )

    @KoinViewModel
    fun provideLoadWalletViewModel(
        requiredValidationUseCase: RequiredValidationUseCase
    ) = LoadWalletViewModel(requiredValidationUseCase)

}