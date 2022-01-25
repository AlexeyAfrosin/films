package com.afrosin.films.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonDetails(
    val id: Long,
    val placeOfBirth: String?
) : Parcelable
