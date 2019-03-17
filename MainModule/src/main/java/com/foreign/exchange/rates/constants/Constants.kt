package com.foreign.exchange.rates.constants

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val BASE_CURRENCY = "BASE_CURRENCY"
const val BASE_DATE = "BASE_DATE"

const val BASE_URL = "https://api.exchangeratesapi.io"

class Constants {
    companion object {

         @SuppressLint("ConstantLocale")
         val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }
}