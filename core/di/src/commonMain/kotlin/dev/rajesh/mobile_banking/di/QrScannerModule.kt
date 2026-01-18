package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.qrscanner.data.remote.QPayRemoteDataSource
import dev.rajesh.mobile_banking.qrscanner.data.remote.QPayRemoteDataSourceImpl
import dev.rajesh.mobile_banking.qrscanner.data.repository.QPayRepositoryImpl
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoder
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoderFactory
import dev.rajesh.mobile_banking.qrscanner.domain.repository.QPayRepository
import dev.rajesh.mobile_banking.qrscanner.domain.usecases.GetQPayMerchantDetailUseCase
import dev.rajesh.mobile_banking.qrscanner.presentation.viewmodel.QrScannerViewModel
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class QrScannerModule {

    @Factory(binds = [QPayRemoteDataSource::class])
    fun provideQPayRemoteDataSource(
        httpClient: HttpClient
    ) = QPayRemoteDataSourceImpl(httpClient = httpClient)

    @Factory(binds = [QPayRepository::class])
    fun provideQPayRepository(
        remoteDataSource: QPayRemoteDataSource
    ) = QPayRepositoryImpl(remoteDataSource = remoteDataSource)

    @Factory
    fun provideGetQPayMerchantDetailUseCase(
        repository: QPayRepository
    ) = GetQPayMerchantDetailUseCase(
        repository = repository
    )

    @KoinViewModel
    fun qrScannerViewModel(
        getQPayMerchantDetailUseCase: GetQPayMerchantDetailUseCase,
        qrDecoder: QrDecoder
    ) = QrScannerViewModel(
        getQPayMerchantDetailUseCase = getQPayMerchantDetailUseCase,
        qrDecoder = qrDecoder
    )

}