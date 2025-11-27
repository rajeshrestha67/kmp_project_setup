package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.menu.ui.model.MenuScreenViewModel
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class MenuScreenModule {

    @KoinViewModel
    fun menuScreenViewModel(
        fetchUserDetailUseCase: FetchUserDetailUseCase
    ) = MenuScreenViewModel(fetchUserDetailUseCase)
}