package com.foreign.exchange.rates.repository

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RemoteRepository {

    @GET
    fun getExchangeRates(@Url date: String, @Query("base") currency: String): Single<Response<ResponseBody>>

}