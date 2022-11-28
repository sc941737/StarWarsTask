package com.sc941737.lib.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO = "io"

val networkModule = module {
    single {
        HttpClient(Android) {
            install(Resources)
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("[HttpClient]", message)
                    }

                }
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
    single(qualifier = named(IO)) {
        Dispatchers.IO
    }
    single {
        NetworkHelper(client = get(), ioDispatcher = get(qualifier = named(IO)))
    }
}