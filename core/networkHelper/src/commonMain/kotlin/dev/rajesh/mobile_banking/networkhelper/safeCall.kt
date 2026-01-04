package dev.rajesh.mobile_banking.networkhelper

import dev.rajesh.mobile_banking.model.AuthError
import dev.rajesh.mobile_banking.model.GenericError
import dev.rajesh.mobile_banking.model.network.DataError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.coroutineContext

val errorJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
    explicitNulls = false
}

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
    } catch (e: CancellationException) {
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
            val message = try {
                val raw = response.bodyAsText()
                decodeErrorMessage(raw, errorJson)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                null
            }

            ApiResult.Error(
                message?.let {
                    DataError.NetworkError.Custom(it)
                } ?: DataError.NetworkError.DataUnknown
            )
        }
    }
}

fun decodeErrorMessage(
    raw: String,
    json: Json
): String? {

    try {
        val auth = json.decodeFromString<AuthError>(raw)
        auth.error_description?.let { return it }
        auth.error?.let { return it }
    } catch (_: SerializationException) {
    }

    try {
        val generic = json.decodeFromString<GenericError>(raw)
        generic.detail?.message?.let { return it }
        generic.message?.let { return it }
    } catch (_: SerializationException) {
    }

    return null
}
