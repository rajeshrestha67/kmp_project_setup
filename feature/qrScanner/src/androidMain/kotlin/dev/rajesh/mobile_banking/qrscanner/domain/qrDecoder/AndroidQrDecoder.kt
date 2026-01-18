package dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AndroidQrDecoder(private val context: Context) : QrDecoder {
    override suspend fun decodeQrFromImage(path: String): Result<String> {
        return try {
            val bitmap = withContext(Dispatchers.IO) {
                context.contentResolver.openInputStream(Uri.parse(path))?.use { input ->
                    BitmapFactory.decodeStream(input)
                }
            } ?: return Result.failure(Exception("Failed to load image"))

            val inputImage = InputImage.fromBitmap(bitmap, 0)
            val scanner = BarcodeScanning.getClient()
            val barcodes = scanner.process(inputImage).await()
            val qrValue = barcodes.firstOrNull()?.rawValue

            if (qrValue != null) Result.success(qrValue)
            else Result.failure(Exception("No QR code found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}