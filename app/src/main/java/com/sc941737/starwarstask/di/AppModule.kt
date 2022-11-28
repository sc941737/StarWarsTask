package com.sc941737.starwarstask.di

import com.sc941737.starwarstask.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(get(), get())
    }
}