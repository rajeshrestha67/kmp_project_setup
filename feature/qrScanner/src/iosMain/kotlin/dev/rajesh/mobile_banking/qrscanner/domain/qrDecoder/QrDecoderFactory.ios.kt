package dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder

actual class QrDecoderFactory {
    actual fun create(): QrDecoder {
        return IOSQrDecoder()
    }
}