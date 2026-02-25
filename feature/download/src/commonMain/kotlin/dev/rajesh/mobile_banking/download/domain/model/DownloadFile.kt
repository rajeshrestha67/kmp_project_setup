package dev.rajesh.mobile_banking.download.domain.model

data class DownloadFile(
    val url: String,
    val fileName: String,
    val mimeType: String? = null
)