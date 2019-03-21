package com.foreign.exchange.rates.repository

import com.foreign.exchange.rates.models.ExchangeRateModel
import io.reactivex.Single
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Inject

class ExchangeRateRepository @Inject constructor() {

    interface API {
        @GET
        fun getExchangeRates(@Url date: String, @Query("base") currency: String): Single<Response<ResponseBody>>
    }

    fun mapResponse(response: String): MutableList<ExchangeRateModel> {
        val list = mutableListOf<ExchangeRateModel>()
        val responseObject = JSONObject(response)
        val ratesObject = responseObject.getJSONObject("rates")
        ratesObject.keys().forEach { list.add(ExchangeRateModel(it, ratesObject.getDouble(it))) }
        return list
    }

    fun filterPLN(list: MutableList<ExchangeRateModel>): MutableList<ExchangeRateModel> {
        val filter = list.filter { it.name == "PLN" }
        return filter as MutableList<ExchangeRateModel>
    }
}