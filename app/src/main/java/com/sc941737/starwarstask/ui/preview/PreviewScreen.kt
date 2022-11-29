package com.sc941737.starwarstask.ui.preview

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sc941737.lib.ui.showToast
import com.sc941737.lib.ui_theme.DefaultButton
import com.sc941737.lib.ui_theme.DefaultToolbar
import com.sc941737.lib.ui_theme.ViewTabList
import com.sc941737.lib.ui_theme.collectAsEffect
import com.sc941737.starwarstask.MainViewModel
import com.sc941737.starwarstask.R
import com.sc941737.starwarstask.ui.selection.SelectionItem
import com.sc941737.starwarstask.ui.selection.UiPerson
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun PreviewScreen(
    navigator: DestinationsNavigator,
    mainViewModel: MainViewModel = koinViewModel(),
    previewViewModel: PreviewViewModel = koinViewModel(),
    context: Context = get(),
) {
    val isFull by mainViewModel.isStarshipFull.collectAsState()
    val viewTabs by previewViewModel.viewTabs.collectAsState()
    val tabs by previewViewModel.tabs.collectAsState()
    val selectedTabIndex by previewViewModel.selectedTabIndex.collectAsState()
    val viewVoyagers by previewViewModel.viewVoyagers.collectAsState()
    val selectedTab = tabs[selectedTabIndex]
    previewViewModel.starshipLaunchedEvent.collectAsEffect {
        context.showToast(it)
    }
    Scaffold(
        topBar = {
            Column {
                DefaultToolbar(
                    titleRes = R.string.screen_preview_title,
                    onClickBack = { navigator.navigateUp() }
                )
                ViewTabList(
                    selectedViewTab = selectedTabIndex,
                    viewTabs = viewTabs,
                    onSelectViewTab = previewViewModel::onClickTab,
                )
            }
        },
        floatingActionButton = {
            if (isFull) {
                FloatingActionButton(
                    onClick = { previewViewModel.launchStarship() },
                ) {
                    Icon(
                        imageVector = Icons.Default.RocketLaunch,
                        contentDescription = stringResource(R.string.preview_launch_btn_content_description),
                    )
                }
            }
        },
        content = { padding ->
            PreviewList(
                people = viewVoyagers,
                onRemovePerson = { mainViewModel.onRemovePerson(it, selectedTab) },
                contentPadding = padding,
            )
        }
    )
}

@Composable
fun PreviewList(
    people: List<UiPerson>,
    onRemovePerson: (UiPerson) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        content = {
            items(items = people, key = { it.id }) {
                SelectionItem(
                    person = it,
                    buttons = { person ->
                        DefaultButton(
                            text = stringResource(R.string.preview_item_btn_remove),
                            modifier = Modifier.weight(1f)
                        ) { onRemovePerson(person) }
                    }
                )
            }
        },
        contentPadding = contentPadding,
    )
}