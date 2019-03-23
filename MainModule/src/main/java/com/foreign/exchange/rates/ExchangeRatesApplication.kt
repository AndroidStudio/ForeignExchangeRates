package com.foreign.exchange.rates

import android.app.Application
import com.foreign.exchange.rates.component.AppComponent
import com.foreign.exchange.rates.component.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class ExchangeRatesApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}