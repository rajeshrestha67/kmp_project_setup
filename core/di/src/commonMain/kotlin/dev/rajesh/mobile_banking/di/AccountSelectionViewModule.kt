package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import dev.rajesh.mobile_banking.useraccounts.presentation.viewmodel.AccountSelectionViewModel
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class AccountSelectionViewModule {

    @Single
    fun provideSelectedAccountStore(): SelectedAccountStore {
        return SelectedAccountStore()
    }

    @KoinViewModel
    fun provideAccountSelectionViewModel(
        fetchUserDetailUseCase: FetchUserDetailUseCase,
        selectedAccountStore: SelectedAccountStore
    ) = AccountSelectionViewModel(fetchUserDetailUseCase, selectedAccountStore)
}