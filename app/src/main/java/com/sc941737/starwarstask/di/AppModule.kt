package com.sc941737.starwarstask.di

import com.sc941737.starwarstask.MainViewModel
import com.sc941737.starwarstask.ui.selection.SelectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(get(), get())
    }
    viewModel {
        SelectionViewModel()
    }
}