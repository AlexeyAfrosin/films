package com.afrosin.films.utils

import com.afrosin.films.model.Film
import com.afrosin.films.model.FilmDTO
import com.afrosin.films.model.FilmsDiscoverDTO

fun convertFilmsDiscoverDtoToFilmList(filmsDiscoverDTO: FilmsDiscoverDTO): List<Film> {
    val filmsDTO: List<FilmDTO> = filmsDiscoverDTO.results!!
    val films = ArrayList<Film>()
    filmsDTO.forEach {
        films.add(
            Film(
                it.title,
                it.poster_path,
                it.overview,
                it.id,
                it.release_date,
                it.vote_average
            )
        )
    }
    return films
}