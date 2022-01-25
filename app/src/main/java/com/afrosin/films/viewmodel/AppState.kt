package com.afrosin.films.viewmodel

import com.afrosin.films.model.Film
import com.afrosin.films.model.Person
import com.afrosin.films.model.PersonDetails

sealed class AppState {
    data class Success(val filmsData: List<Film>) : AppState()
    data class SuccessPopularPerson(val popularPersonData: List<Person>) : AppState()
    data class SuccessPersonDetails(val personDetails: PersonDetails) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}