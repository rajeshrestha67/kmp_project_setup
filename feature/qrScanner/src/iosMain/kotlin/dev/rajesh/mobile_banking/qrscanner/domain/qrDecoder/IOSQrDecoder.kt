package dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.Vision.VNBarcodeObservation
import platform.Vision.VNDetectBarcodesRequest
import platform.Vision.VNImageRequestHandler

class IOSQrDecoder : QrDecoder {


    @OptIn(ExperimentalForeignApi::class)
    override suspend fun decodeQrFromImage(path: String): Result<String> =
        withContext(Dispatchers.Default) {
            try {
                val url = NSURL.Companion.URLWithString(path)
                    ?: NSURL.Companion.fileURLWithPath(path)

                val data = NSData.Companion.dataWithContentsOfURL(url)
                    ?: return@withContext Result.failure(
                        Exception("Failed to load image data")
                    )

                val handler = VNImageRequestHandler(
                    data = data,
                    options = emptyMap<Any?, Any?>()
                )

                val request = VNDetectBarcodesRequest()

                handler.performRequests(
                    listOf(request),
                    error = null
                )

                val observation = request.results
                    ?.firstOrNull() as? VNBarcodeObservation

                val qrValue = observation?.payloadStringValue

                if (qrValue != null) {
                    Result.success(qrValue)
                } else {
                    Result.failure(Exception("No QR code found"))
                }

            } catch (e: Throwable) {
                Result.failure(Exception(e.message ?: "Unknown error"))
            }
        }

}