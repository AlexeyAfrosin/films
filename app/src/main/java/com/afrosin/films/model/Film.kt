package com.afrosin.films.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val title: String = "Фильм тест",
    val posterPath: String? = "",
    val overview: String? = "Описание фильма",
    val id: Long,
    val note: String? = null
) : Parcelable