package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.repository.FundTransferRepository
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases.FetchCoopBranchUseCase
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SelectBranchScreenViewModel
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class CoopBranchModule {

    @KoinViewModel
    fun selectBranchScreenViewModel(
        fetchCoopBranchUseCase: FetchCoopBranchUseCase
    ) = SelectBranchScreenViewModel(fetchCoopBranchUseCase)

    @Factory(binds = [FetchCoopBranchUseCase::class])
    fun provideFetchCoopBranchUseCase(
        repository: FundTransferRepository
    ) = FetchCoopBranchUseCase(repository)


}