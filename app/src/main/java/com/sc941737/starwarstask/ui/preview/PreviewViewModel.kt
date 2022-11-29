package com.sc941737.starwarstask.ui.preview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc941737.lib.error.ErrorEvent
import com.sc941737.lib.error.ErrorTracker
import com.sc941737.lib.starwars_api.StarshipRepository
import com.sc941737.lib.ui.ResourceProvider
import com.sc941737.starwarstask.R
import com.sc941737.starwarstask.VoyagerType
import com.sc941737.starwarstask.ui.selection.UiPerson
import com.sc941737.starwarstask.ui.selection.toUiModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("MoveVariableDeclarationIntoWhen")
class PreviewViewModel(
    private val starshipRepository: StarshipRepository,
    private val errorTracker: ErrorTracker,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {
    companion object {
        const val TAG = "PreviewViewModel"
    }

    val tabs = MutableStateFlow(VoyagerType.values().toList())

    class ViewTabData(val type: VoyagerType, val size: Int, val cap: Int) {
        fun toString(resourceProvider: ResourceProvider) =
            resourceProvider.getString(type.titleStringId, size, cap)
    }
    val viewTabs: StateFlow<List<String>> = starshipRepository.currentStarship
        .filterNotNull()
        .combine(starshipRepository.selectedPeople) { starship, selectedPeople ->
            listOf(
                ViewTabData(
                    type = VoyagerType.PASSENGER,
                    size = selectedPeople.passengers.size,
                    cap = starship.passengersCap,
                ),
                ViewTabData(
                    type = VoyagerType.CREW,
                    size = selectedPeople.crew.size,
                    cap = starship.crewCap,
                ),
            )
        }
        .combine(tabs) { viewTabs, tabs ->
            viewTabs
                .sortedBy { tabs.indexOf(it.type) }
                .map { it.toString(resourceProvider) }
        }
        // Tabs should not be empty at startup
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), VoyagerType.names)

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    private val _starshipLaunchedEvent = MutableSharedFlow<String>()
    val starshipLaunchedEvent: SharedFlow<String> = _starshipLaunchedEvent

    val viewVoyagers: StateFlow<List<UiPerson>> =
        starshipRepository.selectedPeople
            .combine(selectedTabIndex) { people, selectedIndex ->
                val selectedTab = tabs.value[selectedIndex]
                when (selectedTab) {
                    VoyagerType.CREW -> people.crew
                    VoyagerType.PASSENGER -> people.passengers
                }
            }
            .map {
                it.map { person -> person.toUiModel() }
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun launchStarship() {
        if (validateStarshipVoyagers()) {
            starshipRepository.launchStarship()
            viewModelScope.launch {
                _starshipLaunchedEvent.emit(resourceProvider.getString(R.string.preview_starship_launched_event))
            }
        }
    }

    fun onClickTab(index: Int) {
        _selectedTabIndex.update { index }
    }

    private fun validateStarshipVoyagers(): Boolean {
        val isValid = starshipRepository.validateStarshipVoyagers()
        if (!isValid) {
            val event = ErrorEvent(TAG, resourceProvider.getString(R.string.preview_starship_duplicates_found_event))
            errorTracker.reportError(event)
        }
        return isValid
    }
}