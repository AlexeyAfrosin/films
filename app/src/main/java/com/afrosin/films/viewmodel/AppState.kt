package com.afrosin.films.viewmodel

import com.afrosin.films.model.Film

sealed class AppState {
    data class Success(val filmsData: List<Film>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}