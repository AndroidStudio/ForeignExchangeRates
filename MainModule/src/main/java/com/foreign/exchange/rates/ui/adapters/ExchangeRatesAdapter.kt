package com.foreign.exchange.rates.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foreign.exchange.rates.R
import com.foreign.exchange.rates.models.ExchangeRateModel
import com.foreign.exchange.rates.repository.CurrencyRepository
import kotlinx.android.synthetic.main.exchange_rate_list_item.view.*
import javax.inject.Inject

class ExchangeRatesAdapter @Inject constructor(
    context: Context, currencyRepository: CurrencyRepository
) : RecyclerView.Adapter<ExchangeRatesAdapter.ExchangeViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var list: MutableList<ExchangeRateModel> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var currency: String = currencyRepository.getCurrency()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder {
        return ExchangeViewHolder(
            layoutInflater.inflate(R.layout.exchange_rate_list_item, parent, false), currency
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    class ExchangeViewHolder(view: View, private val currency: String) : RecyclerView.ViewHolder(view) {

        private val currencyTextView: TextView = view.currencyTextView
        private val exchangeValueTextView: TextView = view.rateTextView

        fun onBind(exchangeRateModel: ExchangeRateModel) {
            currencyTextView.text = String.format("1 %s = ", currency)
            exchangeValueTextView.text = String.format("%.3f %s", exchangeRateModel.value, exchangeRateModel.name)
        }
    }
}