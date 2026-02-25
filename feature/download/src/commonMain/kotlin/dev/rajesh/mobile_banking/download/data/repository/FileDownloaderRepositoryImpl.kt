package dev.rajesh.mobile_banking.download.data.repository

import dev.rajesh.mobile_banking.download.domain.repository.FileDownloaderRepository
import dev.rajesh.mobile_banking.download.platform.IFileDownloader

class FileDownloaderRepositoryImpl(
    private val fileDownloader: IFileDownloader
) : FileDownloaderRepository {

    override suspend fun downloadFile(
        url: String,
        fileName: String,
        mimeType: String?
    ): Result<String> {
        return fileDownloader.download(url, fileName, mimeType)
    }
}