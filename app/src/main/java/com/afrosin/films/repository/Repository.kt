package com.afrosin.films.repository

import com.afrosin.films.model.FilmsDiscoverDTO
import retrofit2.Callback


interface Repository {
    fun getFilmsFromServer(
        apiKey: String, language: String, callBack: Callback<FilmsDiscoverDTO>,
        includeAdult: Boolean,
        page: Int
    )
}