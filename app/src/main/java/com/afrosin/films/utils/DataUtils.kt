package com.afrosin.films.utils

import com.afrosin.films.model.*

fun convertFilmsDiscoverDtoToFilmList(filmsDiscoverDTO: FilmsDiscoverDTO): List<Film> {
    val filmsDTO: List<FilmDTO> = filmsDiscoverDTO.results!!
    val films = ArrayList<Film>()
    filmsDTO.forEach {
        films.add(Film(it.title, it.poster_path, it.overview, it.id))
    }
    return films
}

fun convertFilmsPersonPopularDtoToFilmsPersonPopularList(filmsPersonPopularDTO: FilmsPersonPopularDTO): List<Person> {
    val personDTO: List<PersonDTO> = filmsPersonPopularDTO.results!!
    val persons = ArrayList<Person>()
    personDTO.forEach {
        persons.add(Person(it.profile_path, it.name, it.id))
    }
    return persons
}

fun convertPersonDetailsDtoToPersonDetails(personDetailsDTO: PersonDetailsDTO): PersonDetails {
    return PersonDetails(personDetailsDTO.id, personDetailsDTO.place_of_birth)
}
