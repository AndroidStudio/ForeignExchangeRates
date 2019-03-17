package com.foreign.exchange.rates.models

import com.google.gson.annotations.SerializedName

data class ExchangeRateModel(
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: Double
)