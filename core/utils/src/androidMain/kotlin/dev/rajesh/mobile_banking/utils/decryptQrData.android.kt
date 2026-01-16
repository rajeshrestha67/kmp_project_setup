package dev.rajesh.mobile_banking.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

actual fun decryptQrData(rawText: String, key: String): String? {
    return try {
        val derivedKey = (key + key).substring(0, 16)
        val keyBytes = derivedKey.toByteArray(Charsets.UTF_8)

        val ivSpec = IvParameterSpec(keyBytes)
        val secretKeySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)

        val decodedData = Base64.decode(rawText, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedData)

        String(decryptedBytes, Charsets.UTF_8)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}