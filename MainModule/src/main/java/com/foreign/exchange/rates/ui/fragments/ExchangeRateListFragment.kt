package com.foreign.exchange.rates.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.foreign.exchange.rates.R
import com.foreign.exchange.rates.constants.BASE_DATE
import com.foreign.exchange.rates.models.ExchangeRateModel
import com.foreign.exchange.rates.viewmodel.ExchangeRatesViewModel

class ExchangeRateListFragment : Fragment() {

    private lateinit var exchangeRatesViewModel: ExchangeRatesViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        exchangeRatesViewModel = ViewModelProviders
            .of(context as FragmentActivity)
            .get(ExchangeRatesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.exchange_rate_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = arguments?.getString(BASE_DATE)
        date?.let {
            exchangeRatesViewModel.getExchangeRates(it,  this::success,  this::error)
        }
    }

    private fun success(list : List<ExchangeRateModel>){
        val size = list.size
    }

    private fun error(error : Throwable){

    }
}