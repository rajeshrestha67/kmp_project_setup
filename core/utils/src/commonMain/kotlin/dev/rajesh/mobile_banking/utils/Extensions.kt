package dev.rajesh.mobile_banking.utils

import io.ktor.http.encodeURLQueryComponent
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.encodeUtf8

fun String?.extractInitials(): String {
    if (this.isNullOrBlank()) return ""

    val nameParts = this.trim().split("\\s+".toRegex()).filter { it.isNotBlank() }

    return when {
        nameParts.isEmpty() -> ""
        nameParts.size == 1 -> nameParts.first().first().uppercase()
        else -> {
            val first = nameParts.first().first().uppercase()
            val last = nameParts.last().first().uppercase()
            "$first$last"
        }
    }
}

fun String.encodeUrl(): String{
    return this.encodeURLQueryComponent()
}


fun String.toBase64(): String =
    encodeUtf8().base64()

fun String.fromBase64(): String =
    decodeBase64()?.utf8()
        ?: error("Invalid Base64 string")