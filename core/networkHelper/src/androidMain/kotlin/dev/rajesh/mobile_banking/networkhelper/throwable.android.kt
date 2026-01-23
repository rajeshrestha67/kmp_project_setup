package dev.rajesh.mobile_banking.networkhelper

import dev.rajesh.mobile_banking.model.network.DataError
import java.net.SocketTimeoutException
import java.net.UnknownHostException

actual fun Throwable.toNetworkError(): DataError.NetworkError {
    return when (this) {
        is UnknownHostException -> DataError.NetworkError.NoInternet
        is SocketTimeoutException -> DataError.NetworkError.RequestTimeout
        else -> {
            println("network error ${this.message}")
            DataError.NetworkError.DataUnknown
        }
    }}