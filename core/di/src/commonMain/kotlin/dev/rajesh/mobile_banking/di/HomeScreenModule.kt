package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.home.presentation.HomeScreenViewModel
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class HomeScreenModule {

    @KoinViewModel
    fun homeScreenViewModel(
        fetchUserDetailUseCase: FetchUserDetailUseCase
    ) = HomeScreenViewModel(fetchUserDetailUseCase)
}