package com.sc941737.starwarstask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc941737.lib.starwars_api.PeopleRepository
import com.sc941737.lib.starwars_api.StarshipRepository
import com.sc941737.lib.ui.launch
import com.sc941737.starwarstask.ui.selection.UiPerson
import com.sc941737.starwarstask.ui.selection.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

enum class TravelType { CREW, PASSENGER }

class MainViewModel(
    private val peopleRepository: PeopleRepository,
    private val starshipRepository: StarshipRepository,
) : ViewModel() {
    private val people = peopleRepository.people
    val starship = starshipRepository.currentStarship
    val selectedPeople = starshipRepository.selectedPeople
    val isStarshipFull = starshipRepository.isStarshipFull
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val viewPeople = people
        .combine(selectedPeople) { people, selectedPeople ->
            people
                .minus(selectedPeople.passengers.toSet())
                .minus(selectedPeople.crew.toSet())
        }
        .map { it.map { person -> person.toUiModel() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    fun onQueryChanged(value: String) = _searchQuery.update { value }

    fun onSelectPerson(uiPerson: UiPerson, group: TravelType) {
        val person = people.value.find { it.id == uiPerson.fullId } ?: return
        when (group) {
            TravelType.CREW -> starshipRepository.addCrewMember(person)
            TravelType.PASSENGER -> starshipRepository.addPassenger(person)
        }
    }

    init {
        launch { fetchCurrentStarship() }
        launch {
            searchQuery.collect {
                fetchPeople(it)
            }
        }
    }

    private fun fetchPeople(query: String) = launch {
        peopleRepository.fetchPeople(query)
    }

    private suspend fun fetchCurrentStarship() = starshipRepository.fetchCurrentStarship()
}