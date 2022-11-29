package com.sc941737.lib.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface ResultError
sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class RequestError(val message: String) : ResultWrapper<Nothing>(), ResultError
    data class ServerError(val message: String) : ResultWrapper<Nothing>(), ResultError
    data class RedirectError(val message: String) : ResultWrapper<Nothing>(), ResultError
    data class UnknownError(val message: String) : ResultWrapper<Nothing>(), ResultError
    object Ignored: ResultWrapper<Nothing>(), ResultError
}

class NetworkHelper(
    private val client: HttpClient,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun <T> apiCall(block: suspend HttpClient.() -> T): T = withContext(ioDispatcher) {
        client.run { block() }
    }

    suspend fun <T> safeApiCall(block: suspend () -> T): ResultWrapper<T> =
        try {
            val value = apiCall { block() }
            ResultWrapper.Success(value)
        } catch (e: RedirectResponseException) { // 3xx
            ResultWrapper.RedirectError("Error: ${e.response.status.description}")
        } catch (e: ClientRequestException) { // 4xx
            ResultWrapper.RequestError("Error: ${e.response.status.description}")
        } catch (e: ServerResponseException) { // 5xx
            ResultWrapper.ServerError("Error: ${e.response.status.description}")
        } catch (e: CancellationException) {
            ResultWrapper.Ignored
        } catch (e: Exception) {
            ResultWrapper.UnknownError("Error: ${e.message}")
        }
}