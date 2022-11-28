package com.sc941737.starwarstask.ui.selection

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sc941737.starwarstask.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
@RootNavGraph(start = true)
fun SelectionScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = koinViewModel(),
) {
    val peopleState by viewModel.people.collectAsState()
    Text("quack: ${peopleState.joinToString("")}")
}