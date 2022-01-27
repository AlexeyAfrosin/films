package com.afrosin.films.repository

import com.afrosin.films.model.FilmsDiscoverDTO
import retrofit2.Callback


class RepositoryImpl(private val remoteDataSource: RemoteDataSource = RemoteDataSource()) :
    Repository {

    override fun getFilmsFromServer(
        apiKey: String,
        language: String,
        callBack: Callback<FilmsDiscoverDTO>,
        includeAdult: Boolean,
        page: Int
    ) {
        remoteDataSource.getFilmsFromServer(apiKey, language, callBack, includeAdult, page)
    }
}