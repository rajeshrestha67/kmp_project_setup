package dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder

import android.content.Context


actual class QrDecoderFactory(private val context: Context) {
    actual fun create(): QrDecoder {
        return AndroidQrDecoder(context)
    }
}