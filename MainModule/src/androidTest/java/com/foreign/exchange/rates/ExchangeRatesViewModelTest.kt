package com.foreign.exchange.rates

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProviders
import androidx.test.rule.ActivityTestRule
import com.foreign.exchange.rates.component.DaggerTestAppComponent
import com.foreign.exchange.rates.modules.ContextModule
import com.foreign.exchange.rates.repository.ExchangeRateRepository
import com.foreign.exchange.rates.ui.MainActivity
import com.foreign.exchange.rates.viewmodel.ExchangeRatesViewModel
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import javax.inject.Inject

class ExchangeRatesViewModelTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    private val testAppComponent by lazy {
        DaggerTestAppComponent.builder()
            .contextModule(ContextModule(activityTestRule.activity.application))
            .build()
    }

    private val viewModel by lazy {
        ViewModelProviders.of(activityTestRule.activity).get(ExchangeRatesViewModel::class.java)
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Before
    fun inject() {
        testAppComponent.inject(this)
    }

    @After
    fun cleanPreferences() {
        sharedPreferences.edit { clear() }
    }

    @Test
    fun getCurrency() {
        Assert.assertEquals(viewModel.getCurrency(), "EUR")
    }

    @Test
    fun setCurrency() {
        val currency = "PLN"
        viewModel.setCurrency(currency)
        Assert.assertEquals(viewModel.getCurrency(), currency)
    }

    @Test
    fun getDateList() {
        val list = viewModel.getDateList()
        assertThat(list.size, not(0))
    }

    @Test
    fun getExchangeRates() {
        val response = viewModel.webService.call(ExchangeRateRepository.API::class.java,
            repo = { it.getExchangeRates("2019-01-26", "PLN") }).blockingGet()
        assertThat(response, notNullValue())
    }
}