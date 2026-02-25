package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.download.data.repository.FileDownloaderRepositoryImpl
import dev.rajesh.mobile_banking.download.domain.repository.FileDownloaderRepository
import dev.rajesh.mobile_banking.download.domain.usecase.DownloadFileUseCase
import dev.rajesh.mobile_banking.download.platform.IFileDownloader
import dev.rajesh.mobile_banking.download.viewModel.DownloadViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module


@Module
class DownloadModule {

    @Factory(binds = [FileDownloaderRepository::class])
    fun provideFileDownloader(
        fileDownloader: IFileDownloader
    ) = FileDownloaderRepositoryImpl(
        fileDownloader = fileDownloader
    )

    @Factory
    fun provideDownloadFileUseCase(
        repository: FileDownloaderRepository
    ) = DownloadFileUseCase(fileDownloaderRepository = repository)

    @KoinViewModel
    fun provideDownloadViewModel(
        downloadFileUseCase: DownloadFileUseCase,
        fileDownloader: IFileDownloader
    ) = DownloadViewModel(downloadFileUseCase, fileDownloader)
}