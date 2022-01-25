package com.afrosin.films.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrosin.films.model.FilmsPersonPopularDTO
import com.afrosin.films.repository.RepositoryImpl
import com.afrosin.films.utils.convertFilmsPersonPopularDtoToFilmsPersonPopularList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val CORRUPTED_DATA = "Неполные данные"

class PopularPersonViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getDataFromFromServer(apiKey: String, language: String) {
        liveDataToObserve.value = AppState.Loading
        repositoryImpl.getPopularPersonFromServer(apiKey, language, callBack)
    }

    private val callBack = object : Callback<FilmsPersonPopularDTO> {

        override fun onResponse(
            call: Call<FilmsPersonPopularDTO>,
            response: Response<FilmsPersonPopularDTO>
        ) {
            val serverResponse: FilmsPersonPopularDTO? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    onResponse(serverResponse)
                } else {
                    onErrorRequest(SERVER_ERROR)
                }
            )
        }

        override fun onFailure(call: Call<FilmsPersonPopularDTO>, t: Throwable) {
            onErrorRequest(t.message.toString())
        }

        private fun onResponse(filmsPersonPopularDTO: FilmsPersonPopularDTO?): AppState {
            return if (filmsPersonPopularDTO == null) {
                onEmptyResponse()
            } else {
                onSuccessResponse(filmsPersonPopularDTO)
            }
        }

        private fun onSuccessResponse(filmsPersonPopularDTO: FilmsPersonPopularDTO): AppState {
            return AppState.SuccessPopularPerson(
                convertFilmsPersonPopularDtoToFilmsPersonPopularList(
                    filmsPersonPopularDTO
                )
            )
        }

        private fun onEmptyResponse(): AppState {
            return onErrorRequest(CORRUPTED_DATA)
        }

        private fun onErrorRequest(errorName: String): AppState {
            return AppState.Error(Throwable(errorName))
        }

    }
}



