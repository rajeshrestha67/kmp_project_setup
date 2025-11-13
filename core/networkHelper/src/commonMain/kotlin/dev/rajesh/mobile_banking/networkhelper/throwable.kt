package dev.rajesh.mobile_banking.networkhelper

import dev.rajesh.mobile_banking.model.network.DataError


expect fun Throwable.toNetworkError(): DataError.NetworkError