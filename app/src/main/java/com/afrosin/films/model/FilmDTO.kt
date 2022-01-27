package com.afrosin.films.model

import java.util.*

data class FilmDTO(
    val adult: Boolean,
    val id: Long,
    val overview: String,
    val poster_path: String,
    val title: String,
    val release_date: Date,
    val vote_average: Float
)
