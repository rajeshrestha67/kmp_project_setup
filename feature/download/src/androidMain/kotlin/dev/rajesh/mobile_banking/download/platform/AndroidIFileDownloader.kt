package dev.rajesh.mobile_banking.download.platform

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AndroidIFileDownloader(
    private val context: Context
) : IFileDownloader {

    override suspend fun download(
        url: String,
        fileName: String,
        mimeType: String?
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val request = DownloadManager.Request(Uri.parse(url)).apply {
                setTitle(fileName)
                setMimeType(mimeType)
                setNotificationVisibility(
                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                )
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    fileName
                )
                setAllowedOverMetered(true)
                setAllowedOverRoaming(true)
            }
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
            Result.success("Downloading file to Downloads/$fileName")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}