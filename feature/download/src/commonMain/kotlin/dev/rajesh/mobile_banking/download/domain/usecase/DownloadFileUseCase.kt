package dev.rajesh.mobile_banking.download.domain.usecase

import dev.rajesh.mobile_banking.download.domain.model.DownloadFile
import dev.rajesh.mobile_banking.download.domain.repository.FileDownloaderRepository

class DownloadFileUseCase(
    private val fileDownloaderRepository: FileDownloaderRepository
) {
    suspend operator fun invoke(file: DownloadFile): Result<String> {
        return fileDownloaderRepository.downloadFile(
            url = file.url,
            fileName = file.fileName,
            mimeType = file.mimeType
        )
    }
}