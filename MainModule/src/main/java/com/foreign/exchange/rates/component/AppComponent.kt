package com.foreign.exchange.rates.component

import com.foreign.exchange.rates.modules.ContextModule
import com.foreign.exchange.rates.modules.NetworkModule
import com.foreign.exchange.rates.modules.PreferencesModule
import com.foreign.exchange.rates.viewmodel.ExchangeRatesViewModel
import dagger.Component

@Component(modules = [ContextModule::class, PreferencesModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(exchangeRatesViewModel: ExchangeRatesViewModel)

}