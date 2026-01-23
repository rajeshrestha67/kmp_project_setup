package dev.rajesh.mobile_banking.networkhelper

import dev.rajesh.mobile_banking.model.network.DataError
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.io.IOException

actual fun Throwable.toNetworkError(): DataError.NetworkError {

    return when (this) {
        is SocketTimeoutException -> DataError.NetworkError.RequestTimeout
        is NotImplementedError -> DataError.NetworkError.Serialization
        is IOException -> DataError.NetworkError.NoInternet
        else -> DataError.NetworkError.DataUnknown
    }

}