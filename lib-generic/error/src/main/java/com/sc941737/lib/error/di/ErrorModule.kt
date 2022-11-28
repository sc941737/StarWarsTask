package com.sc941737.lib.error.di

import com.sc941737.lib.error.ErrorTracker
import com.sc941737.lib.error.ErrorTrackerImpl
import org.koin.dsl.module

val errorModule = module {
    single<ErrorTracker> {
        ErrorTrackerImpl(get())
    }
}