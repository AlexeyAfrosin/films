package com.afrosin.films.model

data class FilmDTO(
    val adult: Boolean,
    val id: Long,
    val overview: String,
    val poster_path: String,
    val title: String
)
