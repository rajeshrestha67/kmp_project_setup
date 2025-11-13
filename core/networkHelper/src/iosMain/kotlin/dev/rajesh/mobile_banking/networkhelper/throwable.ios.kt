package dev.rajesh.mobile_banking.networkhelper

import dev.rajesh.mobile_banking.model.network.DataError
import io.ktor.network.sockets.SocketTimeoutException

actual fun Throwable.toNetworkError(): DataError.NetworkError {
    return when (this) {
//        is UnknownHostException -> DataError.NetworkError.NoInternet
        is SocketTimeoutException -> DataError.NetworkError.RequestTimeout
        else -> DataError.NetworkError.DataUnknown
    }}