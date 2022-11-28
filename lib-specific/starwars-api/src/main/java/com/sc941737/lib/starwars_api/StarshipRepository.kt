package com.sc941737.lib.starwars_api

import com.sc941737.lib.network.NetworkHelper
import com.sc941737.lib.network.ResultWrapper
import com.sc941737.lib.starwars_api.remote.StarWarsApi
import com.sc941737.lib.starwars_api.remote.Starship
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface StarshipRepository{
    val currentStarship: StateFlow<Starship?>

    suspend fun fetchStarship(id: String): Unit?
    suspend fun getStarship(id: String): ResultWrapper<Starship>
    suspend fun fetchCurrentStarship(): Unit?
}

class StarshipRepositoryImpl(
    private val networkHelper: NetworkHelper,
    private val api: StarWarsApi,
) : StarshipRepository, ResultUnwrapper {
    private val _currentStarship = MutableStateFlow<Starship?>(null)
    override val currentStarship: StateFlow<Starship?> = _currentStarship

    override suspend fun fetchStarship(id: String) = executeRequest(
        block = { getStarship(id) },
        onSuccess = { _currentStarship.update { it } },
        onError = {},
    )

    override suspend fun getStarship(id: String) = networkHelper.safeApiCall {
        api.fetchStarship(id)
    }

    override suspend fun fetchCurrentStarship() =
        fetchStarship("10") // Hardcoded 'MilleniumFalcon' for the demo
}