package dev.rajesh.mobile_banking.download.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURLSession
import platform.Foundation.NSUserDomainMask
import platform.Foundation.downloadTaskWithRequest
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalForeignApi::class)
class IosFileDownloader : IFileDownloader {
    override suspend fun download(
        url: String,
        fileName: String,
        mimeType: String?
    ): Result<String> {
        return suspendCancellableCoroutine { continuation ->
            val nsUrl = NSURL.URLWithString(url)
                ?: return@suspendCancellableCoroutine continuation.resumeWithException(
                    IllegalArgumentException("Invalid URL")
                )

            val request = NSURLRequest.requestWithURL(nsUrl)
            val session = NSURLSession.sharedSession
            val task = session.downloadTaskWithRequest(request) { location, _, error ->
                if (error != null) {
                    continuation.resume(Result.failure(Exception(error.localizedDescription)))
                } else if (location != null) {
                    val fileManager = NSFileManager.defaultManager
                    val documentsUrl = fileManager.URLForDirectory(
                        directory = NSDocumentDirectory,
                        inDomain = NSUserDomainMask,
                        appropriateForURL = null,
                        create = false,
                        error = null
                    )

                    val destinationUrl = documentsUrl?.URLByAppendingPathComponent(fileName)
                    if (destinationUrl != null) {
                        fileManager.moveItemAtURL(location, destinationUrl, null)
                        continuation.resume(Result.success(destinationUrl.path ?: ""))
                    } else {
                        continuation.resume(Result.failure(Exception("Failed to save file")))
                    }
                }
            }
            task.resume()
        }
    }
}
