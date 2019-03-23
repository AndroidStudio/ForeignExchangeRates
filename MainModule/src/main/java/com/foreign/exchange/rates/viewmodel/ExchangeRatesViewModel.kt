package com.foreign.exchange.rates.viewmodel

import androidx.lifecycle.ViewModel
import com.foreign.exchange.rates.ExchangeRatesApplication
import com.foreign.exchange.rates.models.ExchangeRateModel
import com.foreign.exchange.rates.repository.CurrencyRepository
import com.foreign.exchange.rates.repository.DateRepository
import com.foreign.exchange.rates.repository.ExchangeRateRepository
import com.foreign.exchange.rates.webservice.WebService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class ExchangeRatesViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var currencyRepository: CurrencyRepository

    @Inject
    lateinit var exchangeRepo: ExchangeRateRepository

    @Inject
    lateinit var dateRepository: DateRepository

    @Inject
    lateinit var webService: WebService

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
        success: (MutableList<ExchangeRateModel>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        compositeDisposable.add(webService.call(
            ExchangeRateRepository.API::class.java,
            repo = { it.getExchangeRates(date, getCurrency()) })
            .map { exchangeRepo.mapResponse(it) }
            //.map { exchangeRepo.filterPLN(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ success(it) }, { error(it) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}