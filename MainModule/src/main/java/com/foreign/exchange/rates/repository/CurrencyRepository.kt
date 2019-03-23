package com.foreign.exchange.rates.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.foreign.exchange.rates.constants.BASE_CURRENCY
import timber.log.Timber
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun setCurrency(currency: String) {

        sharedPreferences.edit { putString(BASE_CURRENCY, currency) }
        Timber.d("CurrencyRepository setCurrency %s", currency)
    }

    fun getCurrency(): String {
        return sharedPreferences.getString(BASE_CURRENCY, "EUR") ?: "EUR"
    }
}