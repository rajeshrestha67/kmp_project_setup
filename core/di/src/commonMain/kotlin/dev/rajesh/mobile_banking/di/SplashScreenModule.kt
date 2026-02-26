package dev.rajesh.mobile_banking.di

import dev.rajesh.datastore.manager.DataStoreManager
import dev.rajesh.mobile_banking.splashscreen.data.dataSource.UserAppPreferenceDataSource
import dev.rajesh.mobile_banking.splashscreen.data.dataSource.UserAppPreferenceDataSourceImpl
import dev.rajesh.mobile_banking.splashscreen.data.repositoryImpl.UserAppPreferenceRepositoryImpl
import dev.rajesh.mobile_banking.splashscreen.domain.UserAppPreferenceRepository
import dev.rajesh.mobile_banking.splashscreen.domain.usecases.CheckHasShownOnboardingUseCase
import dev.rajesh.mobile_banking.splashscreen.domain.usecases.UpdateHasShownOnBoardingUseCase
import dev.rajesh.mobile_banking.splashscreen.viewModel.OnBoardingViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class SplashScreenModule {

    @Factory(binds = [UserAppPreferenceDataSource::class])
    fun provideUserAppPreferenceDataSource(
        dataStoreManager: DataStoreManager
    ) = UserAppPreferenceDataSourceImpl(
        dataStoreManager = dataStoreManager
    )

    @Factory(binds = [UserAppPreferenceRepository::class])
    fun provideUserAppPreferenceRepository(
        userAppPreferenceDataSource: UserAppPreferenceDataSource,
        dataStoreManager: DataStoreManager,
    ) = UserAppPreferenceRepositoryImpl(
        dataStoreManager = dataStoreManager,
        userAppPreferenceDataSource = userAppPreferenceDataSource
    )

    @KoinViewModel
    fun provideOnBoardingViewModel(
        checkHasShownOnboardingUseCase: CheckHasShownOnboardingUseCase,
        updateHasShownOnBoardingUseCase: UpdateHasShownOnBoardingUseCase
    ) = OnBoardingViewModel(
        checkHasShownOnboardingUseCase,
        updateHasShownOnBoardingUseCase
    )


    @Factory
    fun provideCheckHasSeenOnboardingUseCase(
        userAppPreferenceRepository: UserAppPreferenceRepository
    ) = CheckHasShownOnboardingUseCase(
        userAppPreferenceRepository
    )

    @Factory
    fun provideUpdateHasSeenOnBoardingUseCase(
        userAppPreferenceRepository: UserAppPreferenceRepository
    ) = UpdateHasShownOnBoardingUseCase(
        userAppPreferenceRepository = userAppPreferenceRepository
    )


}