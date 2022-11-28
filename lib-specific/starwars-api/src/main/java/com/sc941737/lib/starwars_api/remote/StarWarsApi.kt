package com.sc941737.lib.starwars_api.remote

import com.sc941737.lib.network.NetworkHelper
import com.sc941737.lib.starwars_api.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import com.sc941737.lib.starwars_api.remote.get as safeGet

interface StarWarsApi {
    suspend fun getPeople(query: String): PeopleResponse
    suspend fun fetchStarship(id: String): Starship
}

class StarWarsApiImpl(
    private val networkHelper: NetworkHelper
) : StarWarsApi {
    override suspend fun getPeople(query: String): PeopleResponse = networkHelper.apiCall {
        if (query.isEmpty()) safeGet("people")
        else safeGet("people", "search" to query)
    }

    override suspend fun fetchStarship(id: String): Starship = networkHelper.apiCall {
        safeGet("starships/$id")
    }

}

suspend inline fun <reified T> HttpClient.get(url: String, vararg queryParams: Pair<String, String>) = get {
    val path = "${BuildConfig.baseApiUrl}/$url"
    url(urlString = path)
    queryParams.forEach { (key, value) ->
        parameter(key, value)
    }
}.body<T>()