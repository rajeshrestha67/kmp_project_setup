package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.aboutus.data.remote.CoopDetailRemoteDataSource
import dev.rajesh.mobile_banking.aboutus.data.remote.CoopDetailRemoteDataSourceImpl
import dev.rajesh.mobile_banking.aboutus.data.repository.CoopDetailRepositoryImpl
import dev.rajesh.mobile_banking.aboutus.domain.repository.CoopDetailRepository
import dev.rajesh.mobile_banking.aboutus.domain.usecase.FetchCoopDetailsUseCase
import dev.rajesh.mobile_banking.aboutus.presentation.viewModel.CoopDetailViewModel
import dev.rajesh.mobile_banking.database.dao.CoopDetailDao
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class CoopDetailModule {

    @Factory(binds = [CoopDetailRemoteDataSource::class])
    fun provideCoopDetailRemoteDataSource(
        httpClient: HttpClient
    ) = CoopDetailRemoteDataSourceImpl(httpClient)


    @Factory(binds = [CoopDetailRepository::class])
    fun provideCoopDetailRepository(
        coopDetailRemoteDataSource: CoopDetailRemoteDataSource,
        coopDetailsDao: CoopDetailDao
    ) = CoopDetailRepositoryImpl(coopDetailRemoteDataSource, coopDetailsDao)

    @KoinViewModel
    fun provideCoopDetailVieModel(
        coopDetailsUseCase: FetchCoopDetailsUseCase
    ) = CoopDetailViewModel(coopDetailsUseCase)

    @Factory
    fun provideCoopDetailsUseCase(
        coopDetailRepository: CoopDetailRepository
    ) = FetchCoopDetailsUseCase(coopDetailRepository)
}