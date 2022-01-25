package com.afrosin.films.repository

import com.afrosin.films.model.FilmsDiscoverDTO
import com.afrosin.films.model.FilmsPersonPopularDTO
import com.afrosin.films.model.PersonDetailsDTO
import retrofit2.Callback


class RepositoryImpl(private val remoteDataSource: RemoteDataSource = RemoteDataSource()) :
    Repository {

    override fun getFilmsFromServer(
        apiKey: String,
        language: String,
        callBack: Callback<FilmsDiscoverDTO>,
        includeAdult: Boolean
    ) {
        remoteDataSource.getFilmsFromServer(apiKey, language, callBack, includeAdult)
    }

    override fun getPopularPersonFromServer(
        apiKey: String,
        language: String,
        callBack: Callback<FilmsPersonPopularDTO>
    ) {
        remoteDataSource.getPopularPersonsFromServer(apiKey, language, callBack)
    }

    override fun getPersonDetailsFromServer(
        person_id: Long,
        apiKey: String,
        language: String,
        callBack: Callback<PersonDetailsDTO>
    ) {
        remoteDataSource.getPersonDetailsFromServer(person_id, apiKey, language, callBack)
    }

}