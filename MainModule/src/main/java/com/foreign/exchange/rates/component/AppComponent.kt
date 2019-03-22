package com.foreign.exchange.rates.component

import com.foreign.exchange.rates.modules.ContextModule
import com.foreign.exchange.rates.modules.PreferencesModule
import com.foreign.exchange.rates.ui.fragments.ExchangeRateListFragment
import com.foreign.exchange.rates.viewmodel.ExchangeRatesViewModel
import dagger.Component

@Component(modules = [ContextModule::class, PreferencesModule::class])
interface AppComponent {

    fun inject(exchangeRatesViewModel: ExchangeRatesViewModel)

    fun inject(exchangeRateListFragment: ExchangeRateListFragment)

}