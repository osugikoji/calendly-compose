package com.calendly.compose.data.network

import com.calendly.compose.di.HttpClientProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal class ApiManager(
    private val httpClient: HttpClient = HttpClientProvider.provide(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend inline fun <reified T> request(crossinline block: HttpRequestBuilder.() -> Unit): Result<T> {
         return try {
            withContext(dispatcher) {
                val result = httpClient.request(block).body<DataEnvelop<T>>().data
                Result.success(result)
            }
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }

    @Serializable
    internal data class DataEnvelop<T>(
        @SerialName("data") val data: T,
    )
}
