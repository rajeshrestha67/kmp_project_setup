package dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder

interface QrDecoder {
    suspend fun decodeQrFromImage(path: String): Result<String>
}