package com.foreign.exchange.rates.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.foreign.exchange.rates.ExchangeRatesApplication
import com.foreign.exchange.rates.R
import com.foreign.exchange.rates.constants.BASE_DATE
import com.foreign.exchange.rates.models.ExchangeRateModel
import com.foreign.exchange.rates.ui.adapters.ExchangeRatesAdapter
import com.foreign.exchange.rates.viewmodel.ExchangeRatesViewModel
import kotlinx.android.synthetic.main.exchange_rate_fragment.view.*
import timber.log.Timber
import javax.inject.Inject

class ExchangeRateListFragment : Fragment() {

    private lateinit var exchangeRatesViewModel: ExchangeRatesViewModel

    @Inject
    lateinit var exchangeAdapter: ExchangeRatesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        exchangeRatesViewModel = ViewModelProviders
            .of(context as FragmentActivity)
            .get(ExchangeRatesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExchangeRatesApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.exchange_rate_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exchangeAdapter.clear()

        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = exchangeAdapter
        view.progressBar?.visibility = View.GONE
        loadRates()
    }

    private fun loadRates() {
        view?.progressBar?.visibility = View.VISIBLE
        val date = arguments?.getString(BASE_DATE)
        date?.let {
            exchangeRatesViewModel.getExchangeRates(it, this::success, this::error)
        }
    }

    private fun success(list: MutableList<ExchangeRateModel>) {
        view?.progressBar?.visibility = View.GONE
        exchangeAdapter.list = list
    }

    private fun error(error: Throwable) {
        Timber.d("ExchangeRateListFragment error: %s", error.printStackTrace())
    }
}