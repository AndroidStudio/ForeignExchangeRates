package com.foreign.exchange.rates.component

import android.content.Context
import com.foreign.exchange.rates.modules.PreferencesModule
import com.foreign.exchange.rates.ui.fragments.ExchangeRateListFragment
import com.foreign.exchange.rates.viewmodel.ExchangeRatesViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [PreferencesModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder

    }

    fun inject(exchangeRatesViewModel: ExchangeRatesViewModel)

    fun inject(exchangeRateListFragment: ExchangeRateListFragment)

}