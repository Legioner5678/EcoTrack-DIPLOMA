package com.example.ecotrack.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun today(): String {
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return format.format(Date())
    }
}
