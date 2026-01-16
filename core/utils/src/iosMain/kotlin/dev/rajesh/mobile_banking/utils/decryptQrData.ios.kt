package dev.rajesh.mobile_banking.utils

import platform.Foundation.*
import platform.CoreCrypto.*
import kotlinx.cinterop.*
import platform.posix.size_tVar

@OptIn(ExperimentalForeignApi::class)
actual fun decryptQrData(rawText: String, key: String): String? {
    val derivedKey = (key + key).substring(0, 16)
    val keyBytes = derivedKey.encodeToByteArray()

    val decodedData = NSData.create(base64EncodedString = rawText, options = 0u) ?: return null
    val dataLength = decodedData.length.toInt()
    val bufferSize = dataLength + kCCBlockSizeAES128.toInt()
    val buffer = ByteArray(bufferSize)

    return buffer.usePinned { pinnedBuffer ->
        keyBytes.usePinned { pinnedKey ->
            memScoped {
                val bytesDecrypted = alloc<size_tVar>()
                val status = CCCrypt(
                    kCCDecrypt,
                    kCCAlgorithmAES,
                    kCCOptionPKCS7Padding,
                    pinnedKey.addressOf(0), kCCKeySizeAES128.toULong(),
                    pinnedKey.addressOf(0), // Using key as IV per your requirement
                    decodedData.bytes, decodedData.length,
                    pinnedBuffer.addressOf(0), bufferSize.toULong(),
                    bytesDecrypted.ptr
                )

                if (status == kCCSuccess) {
                    val resultBytes = buffer.sliceArray(0 until bytesDecrypted.value.toInt())
                    resultBytes.decodeToString()
                } else {
                    null
                }
            }
        }
    }
}