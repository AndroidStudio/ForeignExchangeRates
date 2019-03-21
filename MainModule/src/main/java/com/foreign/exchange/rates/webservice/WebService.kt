package com.foreign.exchange.rates.webservice

import android.content.Context
import android.net.ConnectivityManager
import com.foreign.exchange.rates.BuildConfig
import com.foreign.exchange.rates.constants.BASE_URL
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.internal.platform.Platform
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WebService @Inject constructor(context: Context) {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private val connectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as? ConnectivityManager
    }

    fun <T> call(repository: Class<T>, createRetrofit: (T) -> Single<Response<ResponseBody>>): Single<String> {
        val request = createRetrofit(retrofit.create(repository))
        return isInternetConnection()
            .flatMap { request }
            .flatMap { mapResponse(it) }
    }

    private fun createOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(createLoggingInterceptor())
        }
        return okHttpClientBuilder.build()
    }

    private fun createLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .loggable(true)
            .setLevel(Level.BASIC)
            .log(Platform.WARN)
            .request("REQUEST")
            .response("RESPONSE")
            .build()
    }

    private fun isInternetConnection(): Single<Boolean> {
        return Single.create {
            val activeNetwork = connectivityManager?.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected) {
                it.onSuccess(true)
            } else {
                it.onError(Exception("No internet connection"))
            }
        }
    }

    private fun mapResponse(response: Response<ResponseBody>): Single<String> {
        return Single.create {
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                it.onSuccess(responseBody.string())
            } else {
                it.onError(Exception("Connection error"))
            }
        }
    }
}