package com.sc941737.lib.starwars_api

import com.sc941737.lib.error.ErrorEvent
import com.sc941737.lib.error.ErrorTracker
import com.sc941737.lib.network.NetworkHelper
import com.sc941737.lib.network.ResultWrapper
import com.sc941737.lib.starwars_api.remote.PeopleResponse
import com.sc941737.lib.starwars_api.remote.Person
import com.sc941737.lib.starwars_api.remote.StarWarsApi
import com.sc941737.lib.starwars_api.remote.toDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface PeopleRepository {
    companion object {
        const val TAG = "PeopleRepository"
    }

    val people: StateFlow<List<Person>>
    suspend fun fetchPeople(query: String): Unit?
    suspend fun getPeople(query: String): ResultWrapper<PeopleResponse>
}

class PeopleRepositoryImpl(
    private val networkHelper: NetworkHelper,
    private val api: StarWarsApi,
    private val errorTracker: ErrorTracker,
) : PeopleRepository, ResultUnwrapper {
    private val _people = MutableStateFlow<List<Person>>(emptyList())
    override val people: StateFlow<List<Person>> = _people

    override suspend fun fetchPeople(query: String) = executeRequest(
        block = { getPeople(query) },
        onSuccess = { response -> _people.update { response.toDataModel() } },
        onError = {
            val event = ErrorEvent(PeopleRepository.TAG, it)
            errorTracker.reportError(event)
        },
    )

    override suspend fun getPeople(query: String): ResultWrapper<PeopleResponse> =
        networkHelper.safeApiCall {
            api.getPeople(query)
        }
}