package com.foreign.exchange.rates.repository

import com.foreign.exchange.rates.constants.Constants.Companion.DATE_FORMAT
import java.util.*
import javax.inject.Inject

class DateRepository @Inject constructor() {

    fun getDateList(): ArrayList<String> {

        val list = arrayListOf<String>()

        val startDate: Calendar = Calendar.getInstance()
        startDate.set(2000,1,1)

        val currentDate: Calendar = Calendar.getInstance()
        while (startDate.before(currentDate)) {
            startDate.set(Calendar.DATE, startDate.get(Calendar.DATE) + 1)
            list.add(DATE_FORMAT.format(startDate.time))
        }
        return list
    }
}