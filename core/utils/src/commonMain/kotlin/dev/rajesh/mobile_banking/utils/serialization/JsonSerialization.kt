package dev.rajesh.mobile_banking.utils.serialization

import kotlinx.serialization.json.Json

val AppJson = Json{
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    prettyPrint = true
}