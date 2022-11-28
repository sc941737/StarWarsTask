package com.sc941737.lib.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


fun <T> LifecycleOwner.subscribe(sharedFlow: SharedFlow<T>, block: (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        sharedFlow.collectLatest {
            block(it)
        }
    }
}