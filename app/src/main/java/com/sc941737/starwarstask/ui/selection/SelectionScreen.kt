package com.sc941737.starwarstask.ui.selection

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sc941737.lib.ui.drawableId
import com.sc941737.lib.ui_theme.DefaultButton
import com.sc941737.lib.ui_theme.DefaultDivider
import com.sc941737.lib.ui_theme.DefaultToolbar
import com.sc941737.lib.ui_theme.Dimensions
import com.sc941737.lib.ui_theme.SearchToolbar
import com.sc941737.starwarstask.MainViewModel
import com.sc941737.starwarstask.R
import com.sc941737.starwarstask.TravelType
import com.sc941737.starwarstask.ui.destinations.PreviewScreenDestination
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
@RootNavGraph(start = true)
fun SelectionScreen(
    navigator: DestinationsNavigator,
    mainViewModel: MainViewModel = koinViewModel(),
    selectionViewModel: SelectionViewModel = koinViewModel(),
) {
    // State
    val people by mainViewModel.viewPeople.collectAsState()
    val searchQuery by mainViewModel.searchQuery.collectAsState()
    val isSearching by selectionViewModel.isSearching.collectAsState()
    val starship by mainViewModel.starship.collectAsState()
    val selectedPeople by mainViewModel.selectedPeople.collectAsState()
    val isStarshipFull by mainViewModel.isStarshipFull.collectAsState()
    // Navigation
    fun navigateToPreview() = navigator.navigate(PreviewScreenDestination)
    // UI
    Scaffold(
        topBar = {
             SelectionScreenToolbar(
                 isSearching = isSearching,
                 searchQuery = searchQuery,
                 onSearchTermChanged = mainViewModel::onQueryChanged,
                 onClickCancelSearch = selectionViewModel::onClickCancelSearch,
                 onClickSearch = selectionViewModel::onClickSearch,
                 onClickPreview = ::navigateToPreview,
             )
        },
        content = { padding ->
            Column {
                starship?.let {
                    CapacityCounters(
                        crewCap = it.crewCap,
                        currentCrewSize = selectedPeople.crew.size,
                        passengerCap = it.passengersCap,
                        currentPassengerSize = selectedPeople.passengers.size,
                        isFull = isStarshipFull,
                    )
                }
                DefaultDivider()
                SelectionList(
                    people = people,
                    onSelectPerson = mainViewModel::onSelectPerson,
                    contentPadding = padding,
                )   
            }
        },
    )
}

@Composable
fun SelectionScreenToolbar(
    isSearching: Boolean,
    searchQuery: String,
    onSearchTermChanged: (String) -> Unit,
    onClickCancelSearch: () -> Unit,
    onClickSearch: () -> Unit,
    onClickPreview: () -> Unit,
) {
    if (isSearching) {
        SearchToolbar(
            searchTerm = searchQuery,
            onSearchTermChanged = onSearchTermChanged,
            onCancelSearchClicked = onClickCancelSearch,
        )
    } else {
        DefaultToolbar(
            titleRes = R.string.screen_selection_title,
            canNavigateBack = false,
            actions = {
                IconButton(onClick = onClickSearch) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.selection_toolbar_menu_search),
                        tint = Color.White,
                    )
                }
                IconButton(onClick = onClickPreview) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = stringResource(R.string.selection_toolbar_menu_preview),
                        tint = Color.White,
                    )
                }
            }
        )
    }
}

@Composable
fun SelectionList(
    people: List<UiPerson>,
    onSelectPerson: (UiPerson, TravelType) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        content = {
            items(items = people, key = { it.id }) {
                SelectionItem(person = it, onClick = onSelectPerson)
            }
        },
        contentPadding = contentPadding,
    )
}

@Composable
@SuppressLint("DiscouragedApi")
fun SelectionItem(
    person: UiPerson,
    context: Context = get(),
    onClick: (UiPerson, TravelType) -> Unit,
) {
    Column {
        val imageId = context.drawableId(person.imageName)
        val drawable = ContextCompat.getDrawable(context, imageId)!!.toBitmap().asImageBitmap()
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(Dimensions.medium)) {
            Image(bitmap = drawable, contentDescription = null)
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = Dimensions.small)) {
                Text(text = stringResource(id = R.string.selection_item_name, person.name))
                Row (horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.selection_item_birth_year, person.birthYear))
                    Text(text = stringResource(id = R.string.selection_item_gender, person.gender))
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                DefaultButton(
                    text = stringResource(R.string.selection_item_btn_add_crew),
                    modifier = Modifier.weight(1f)
                ) { onClick(person, TravelType.CREW) }
                DefaultButton(
                    text = stringResource(R.string.selection_item_btn_add_passengers),
                    modifier = Modifier.weight(1f)
                ) { onClick(person, TravelType.PASSENGER) }
            }
        }
        DefaultDivider()
    }
}

@Composable
fun CapacityCounters(
    crewCap: Int,
    currentCrewSize: Int,
    passengerCap: Int,
    currentPassengerSize: Int,
    isFull: Boolean,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(Dimensions.medium)
            .fillMaxWidth(),
    ) {
        if (isFull) {
            Text(
                text = stringResource(R.string.selection_counter_full),
                fontWeight = FontWeight.Bold,
                color = Color.Red,
            )
        } else {
            Text(
                text = stringResource(id = R.string.selection_counter_crew, currentCrewSize, crewCap),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(id = R.string.selection_counter_passengers, currentPassengerSize, passengerCap),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}