package com.sc941737.lib.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(Android) {
            install(Resources)
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    },
                    contentType = ContentType.Application.Json,
                )
            }
        }
    }
    single(qualifier = named("io")) {
        Dispatchers.IO
    }
    single {
        NetworkHelper(client = get(), ioDispatcher = get(qualifier = named("io")))
    }
}