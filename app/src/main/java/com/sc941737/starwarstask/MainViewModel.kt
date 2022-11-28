package com.sc941737.starwarstask

import androidx.lifecycle.ViewModel
import com.sc941737.lib.starwars_api.PeopleRepository
import com.sc941737.lib.starwars_api.StarshipRepository
import com.sc941737.lib.ui.launch

class MainViewModel(
    private val peopleRepository: PeopleRepository,
    private val starshipRepository: StarshipRepository,
) : ViewModel() {
    val people = peopleRepository.people

    init {
        launch { fetchCurrentStarship() }
    }

    fun fetchPeople(query: String) = launch {
        peopleRepository.fetchPeople(query)
    }

    private suspend fun fetchCurrentStarship() = starshipRepository.fetchCurrentStarship()
}