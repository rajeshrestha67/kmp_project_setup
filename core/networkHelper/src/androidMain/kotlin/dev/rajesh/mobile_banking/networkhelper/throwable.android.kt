package dev.rajesh.mobile_banking.networkhelper

import dev.rajesh.mobile_banking.model.network.DataError

actual fun Throwable.toNetworkError(): DataError.NetworkError {
    TODO("Not yet implemented")
}