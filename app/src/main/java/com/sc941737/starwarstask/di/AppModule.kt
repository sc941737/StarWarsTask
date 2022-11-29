package com.sc941737.starwarstask.di

import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.coroutineScope
import com.sc941737.starwarstask.MainViewModel
import com.sc941737.starwarstask.ui.preview.PreviewViewModel
import com.sc941737.starwarstask.ui.selection.SelectionViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        ProcessLifecycleOwner.get().lifecycle.coroutineScope
    }
    viewModel {
        MainViewModel(get(), get(), get())
    }
    viewModel {
        SelectionViewModel()
    }
    viewModel {
        PreviewViewModel(get(), get(), get())
    }
}