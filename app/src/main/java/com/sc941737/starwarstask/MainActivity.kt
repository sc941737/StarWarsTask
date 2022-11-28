package com.sc941737.starwarstask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import com.sc941737.lib.ui.showToast
import com.sc941737.lib.ui.subscribe
import com.sc941737.lib.ui_theme.StarWarsTaskTheme
import com.sc941737.starwarstask.ui.NavGraphs
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsTaskTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
        subscribe(viewModel.peopleErrorEvents) { showToast(it.message) }
        subscribe(viewModel.starshipErrorEvents) { showToast(it.message) }
    }
}