package dev.rajesh.mobile_banking.networkhelper

import dev.rajesh.mobile_banking.model.network.DataError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.coroutineContext


suspend inline fun <reified T> safeCall(
    crossinline execute: suspend () -> HttpResponse
): ApiResult<T, DataError.NetworkError> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return ApiResult.Error(DataError.NetworkError.RequestTimeout)
    } catch (e: UnresolvedAddressException) {
        return ApiResult.Error(DataError.NetworkError.NoInternet)
    } catch (e: SerializationException) {
        return ApiResult.Error(DataError.NetworkError.Serialization)
    }catch (e: CancellationException) {
        return ApiResult.Error(DataError.NetworkError.RequestTimeout)
    } catch (e: Throwable) {
        return ApiResult.Error(e.toNetworkError())
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return ApiResult.Error(DataError.NetworkError.DataUnknown)
    }
    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): ApiResult<T, DataError.NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                ApiResult.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                ApiResult.Error(DataError.NetworkError.Serialization)
            } catch (e: JsonConvertException) {
                ApiResult.Error(DataError.NetworkError.Serialization)
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                ApiResult.Error(DataError.NetworkError.Serialization)
            }
        }

        401 -> ApiResult.Error(DataError.NetworkError.UnAuthorized)
        408 -> ApiResult.Error(DataError.NetworkError.RequestTimeout)
        409 -> ApiResult.Error(DataError.NetworkError.Conflict)
        429 -> ApiResult.Error(DataError.NetworkError.TooManyRequest)
        413 -> ApiResult.Error(DataError.NetworkError.PayloadTooLarge)
        in 500..599 -> ApiResult.Error(DataError.NetworkError.Server)
        else -> {

            /*if (T::class == LoginResponseDto::class || T::class == LeaveRequestResponseDto::class  || T::class == AttendanceRequestResponseDto::class ) {
                val res = try {
                    response.body<ErrorData>()
                } catch (e: Exception) {
                    null
                }
                ApiResult.Error(
                    DataError.NetworkError.Custom(
                        res?.message ?: response.status.description
                    )
                )
            } else {
                ApiResult.Error(DataError.NetworkError.DataUnknown)
            }*/
            ApiResult.Error(DataError.NetworkError.DataUnknown)
        }
    }
}
