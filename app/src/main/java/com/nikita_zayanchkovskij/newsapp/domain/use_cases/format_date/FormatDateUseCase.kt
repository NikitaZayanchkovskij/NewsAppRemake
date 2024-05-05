package com.nikita_zayanchkovskij.newsapp.domain.use_cases.format_date


import java.text.SimpleDateFormat
import java.util.Locale


object FormatDateUseCase {

    fun formatDate(dateToFormat: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
        val date = sdf.parse(dateToFormat)
        sdf.applyPattern("dd.MM.yyyy HH:mm")
        val dateToReturn = sdf.format(date!!)
        return dateToReturn
    }
}