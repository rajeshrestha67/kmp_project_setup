package dev.rajesh.mobile_banking.download.domain.repository

interface FileDownloaderRepository {

    suspend fun downloadFile(
        url: String,
        fileName: String,
        mimeType: String? = null,
    ): Result<String> //returns saved file path
}