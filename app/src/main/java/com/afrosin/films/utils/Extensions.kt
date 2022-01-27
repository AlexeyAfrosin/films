package com.afrosin.films.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.toStringFormat(patternDate: String = "dd.MM.yyyy HH:mm"): String =
    SimpleDateFormat(patternDate, Locale.getDefault()).format(this)
