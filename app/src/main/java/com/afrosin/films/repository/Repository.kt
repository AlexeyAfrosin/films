package com.afrosin.films.repository

import com.afrosin.films.model.FilmsDiscoverDTO
import com.afrosin.films.model.FilmsPersonPopularDTO
import com.afrosin.films.model.PersonDetailsDTO
import retrofit2.Callback


interface Repository {
    fun getFilmsFromServer(
        apiKey: String, language: String, callBack: Callback<FilmsDiscoverDTO>,
        includeAdult: Boolean
    )

    fun getPopularPersonFromServer(
        apiKey: String,
        language: String,
        callBack: Callback<FilmsPersonPopularDTO>
    )

    fun getPersonDetailsFromServer(
        person_id: Long,
        apiKey: String,
        language: String,
        callBack: Callback<PersonDetailsDTO>
    )
}