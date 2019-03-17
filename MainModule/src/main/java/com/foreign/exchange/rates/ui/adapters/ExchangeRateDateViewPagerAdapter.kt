package com.foreign.exchange.rates.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.foreign.exchange.rates.constants.BASE_DATE
import com.foreign.exchange.rates.constants.Constants
import com.foreign.exchange.rates.ui.fragments.ExchangeRateListFragment
import java.util.*

class ExchangeRateDateViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val list: ArrayList<String>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        val exchangeRateListFragment = ExchangeRateListFragment()
        exchangeRateListFragment.arguments = Bundle().apply { putString(BASE_DATE, list[position]) }
        return exchangeRateListFragment
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position]
    }
}