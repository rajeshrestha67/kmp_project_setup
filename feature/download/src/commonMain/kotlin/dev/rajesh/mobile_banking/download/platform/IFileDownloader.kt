package dev.rajesh.mobile_banking.download.platform

interface IFileDownloader {
    suspend fun download(
        url: String,
        fileName: String,
        mimeType: String? = null
    ): Result<String>

    fun openFile(filePath: String)
}