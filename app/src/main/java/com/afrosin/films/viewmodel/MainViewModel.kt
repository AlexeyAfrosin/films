package com.afrosin.films.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrosin.films.model.FilmsDiscoverDTO
import com.afrosin.films.repository.RepositoryImpl
import com.afrosin.films.utils.convertFilmsDiscoverDtoToFilmList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val CORRUPTED_DATA = "Неполные данные"

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getDataFromFromServer(apiKey: String, language: String, includeAdult: Boolean) {
        liveDataToObserve.value = AppState.Loading
        repositoryImpl.getFilmsFromServer(apiKey, language, callBack, includeAdult)
    }

    private val callBack = object : Callback<FilmsDiscoverDTO> {

        override fun onResponse(
            call: Call<FilmsDiscoverDTO>,
            response: Response<FilmsDiscoverDTO>
        ) {
            val serverResponse: FilmsDiscoverDTO? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    onResponse(serverResponse)
                } else {
                    onErrorRequest(SERVER_ERROR)
                }
            )
        }

        override fun onFailure(call: Call<FilmsDiscoverDTO>, t: Throwable) {
            onErrorRequest(t.message.toString())
        }

        private fun onResponse(filmsDiscoverDTO: FilmsDiscoverDTO?): AppState {
            return if (filmsDiscoverDTO == null) {
                onEmptyResponse()
            } else {
                onSuccessResponse(filmsDiscoverDTO)
            }
        }

        private fun onSuccessResponse(filmsDiscoverDTO: FilmsDiscoverDTO): AppState {
            return AppState.Success(convertFilmsDiscoverDtoToFilmList(filmsDiscoverDTO))
        }

        private fun onEmptyResponse(): AppState {
            return onErrorRequest(CORRUPTED_DATA)
        }

        private fun onErrorRequest(errorName: String): AppState {
            return AppState.Error(Throwable(errorName))
        }

    }
}