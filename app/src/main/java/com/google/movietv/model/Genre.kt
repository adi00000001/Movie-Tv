package com.google.movietv.model


data class GenreResponse(
    val genres: List<Genre>? = null
)

data class Genre(
    val id: Long? = null,
    val name: String? = null
)