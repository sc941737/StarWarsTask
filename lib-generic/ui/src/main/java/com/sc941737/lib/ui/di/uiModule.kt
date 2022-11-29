package com.sc941737.lib.ui.di

import com.sc941737.lib.ui.ResourceProvider
import com.sc941737.lib.ui.ResourceProviderImpl
import org.koin.dsl.module

val uiModule = module {
    single<ResourceProvider> {
        ResourceProviderImpl(get())
    }
}