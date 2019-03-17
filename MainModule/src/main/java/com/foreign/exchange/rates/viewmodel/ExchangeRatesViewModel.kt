package com.foreign.exchange.rates.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.foreign.exchange.rates.ExchangeRatesApplication
import com.foreign.exchange.rates.models.ExchangeRateModel
import com.foreign.exchange.rates.repository.CurrencyRepository
import com.foreign.exchange.rates.repository.DateRepository
import com.foreign.exchange.rates.repository.RemoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.util.*
import javax.inject.Inject


class ExchangeRatesViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var remoteRepository: RemoteRepository

    @Inject
    lateinit var currencyRepository: CurrencyRepository

    @Inject
    lateinit var dateRepository: DateRepository

    init {
        ExchangeRatesApplication.appComponent.inject(this)
    }

    fun getCurrency(): String {
        return currencyRepository.getCurrency()
    }

    fun setCurrency(currency: String) {
        currencyRepository.setCurrency(currency)
    }

    fun getDateList(): ArrayList<String> {
        return dateRepository.getDateList()
    }

    fun getExchangeRates(
        date: String,
        success: (List<ExchangeRateModel>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            remoteRepository.getExchangeRates(date, getCurrency())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(::mapResponse)
                .subscribe({ success(it) }, { error(it) })
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun mapResponse(response: Response<ResponseBody>): List<ExchangeRateModel> {
        val list = arrayListOf<ExchangeRateModel>()
        val body = response.body()?.string()
        val responseObject = JSONObject(body)
        val ratesObject = responseObject.getJSONObject("rates")
        ratesObject.keys().forEach { list.add(ExchangeRateModel(it, ratesObject.getDouble(it))) }
        return list
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}