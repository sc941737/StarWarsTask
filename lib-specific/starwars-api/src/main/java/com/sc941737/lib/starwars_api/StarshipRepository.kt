package com.sc941737.lib.starwars_api

import com.sc941737.lib.network.NetworkHelper
import com.sc941737.lib.network.ResultWrapper
import com.sc941737.lib.starwars_api.remote.StarWarsApi
import com.sc941737.lib.starwars_api.remote.Starship
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class SelectedPeople(
    val crew: List<Person>,
    val passengers: List<Person>,
) {
    companion object {
        val Empty = SelectedPeople(crew = emptyList(), passengers = emptyList())
    }
}

interface StarshipRepository {
    val currentStarship: StateFlow<Starship?>
    val selectedPeople: StateFlow<SelectedPeople>
    val isStarshipFull: Flow<Boolean>

    suspend fun fetchStarship(id: String): Unit?
    suspend fun getStarship(id: String): ResultWrapper<Starship>
    suspend fun fetchCurrentStarship(): Unit?

    fun addCrewMember(person: Person)
    fun addPassenger(person: Person)
    fun removeCrewMember(person: Person)
    fun removePassenger(person: Person)
}

class StarshipRepositoryImpl(
    private val networkHelper: NetworkHelper,
    private val api: StarWarsApi,
) : StarshipRepository, ResultUnwrapper {
    private val _currentStarship = MutableStateFlow<Starship?>(null)
    override val currentStarship: StateFlow<Starship?> = _currentStarship

    private val _selectedPeople = MutableStateFlow(SelectedPeople.Empty)
    override val selectedPeople: StateFlow<SelectedPeople> = _selectedPeople

    private val _isStarshipFull = _currentStarship.combine(_selectedPeople) { starship, people ->
        starship ?: return@combine false
        people.crew.size == starship.crewCap && people.passengers.size == starship.passengersCap
    }
    override val isStarshipFull: Flow<Boolean> = _isStarshipFull
    override fun addCrewMember(person: Person) =
        _selectedPeople.update {
            val crewCap = currentStarship.value?.crewCap ?: return@update it
            val newCrew = it.crew.plusLimited(person, crewCap)
            it.copy(crew = newCrew)
        }
    override fun addPassenger(person: Person) =
        _selectedPeople.update {
            val passengerCap = currentStarship.value?.passengersCap ?: return@update it
            val newPassengers = it.passengers.plusLimited(person, passengerCap)
            it.copy(passengers = newPassengers)
        }
    override fun removeCrewMember(person: Person) =
        _selectedPeople.update { it.copy(crew = it.crew.minus(person)) }

    override fun removePassenger(person: Person) =
        _selectedPeople.update { it.copy(passengers = it.passengers.minus(person)) }

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

fun <T> List<T>.plusUnique(element: T): List<T> {
    return if (element in this) this
    else plus(element)
}

fun <T> List<T>.plusLimited(element: T, limit: Int): List<T> {
    return if (size == limit) this
    else plusUnique(element)
}