package com.sc941737.starwarstask

import android.app.Application
import com.sc941737.lib.network.networkModule
import com.sc941737.starwarstask.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, networkModule)
        }
    }
}