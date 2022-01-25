package com.afrosin.films.model

data class FilmsDiscoverDTO(
    val page: Int,
    val results: List<FilmDTO>,
    val total_pages: Int,
    val total_results: Int
)