package com.sc941737.starwarstask

import android.app.Application
import com.sc941737.lib.error.di.errorModule
import com.sc941737.lib.network.networkModule
import com.sc941737.lib.starwars_api.di.apiModule
import com.sc941737.lib.ui.di.uiModule
import com.sc941737.starwarstask.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                uiModule,
                appModule,
                errorModule,
                networkModule,
                apiModule
            )
        }
    }
}