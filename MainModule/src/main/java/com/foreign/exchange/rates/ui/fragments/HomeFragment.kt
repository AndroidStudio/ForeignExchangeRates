package com.foreign.exchange.rates.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.foreign.exchange.rates.R
import com.foreign.exchange.rates.constants.Constants
import com.foreign.exchange.rates.ui.adapters.ExchangeRateDateViewPagerAdapter
import com.foreign.exchange.rates.viewmodel.ExchangeRatesViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*

class HomeFragment : Fragment(), CalendarDatePickerDialogFragment.OnDateSetListener {

    private lateinit var exchangeRatesViewModel: ExchangeRatesViewModel

    private val dateList by lazy { exchangeRatesViewModel.getDateList() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        exchangeRatesViewModel = ViewModelProviders
            .of(context as FragmentActivity)
            .get(ExchangeRatesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exchangeRateDateViewPagerAdapter = ExchangeRateDateViewPagerAdapter(childFragmentManager, dateList)
        viewPager.adapter = exchangeRateDateViewPagerAdapter
        viewPager.currentItem = exchangeRateDateViewPagerAdapter.count - 1
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            com.foreign.exchange.rates.R.id.select_date -> showDatePicker()
        }
        return true
    }

    private fun showDatePicker() {
        val datePicker = CalendarDatePickerDialogFragment()
            .setOnDateSetListener(this)
        datePicker.show(childFragmentManager, null)
    }

    override fun onDateSet(dialog: CalendarDatePickerDialogFragment?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.YEAR, year)

        setDate(calendar)
    }

    private fun setDate(calendar: Calendar) {
        val date = Constants.DATE_FORMAT.format(calendar.time)
        val index = dateList.indexOf(date)
        if (index > 0) {
            viewPager.currentItem = index
        }
    }
}