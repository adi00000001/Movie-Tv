package com.google.movietv.network

import com.google.movietv.model.Genre
import com.google.movietv.model.GenreResponse
import com.google.movietv.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular?language=en-US")
    suspend fun getMovies(
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("genre/movie/list?language=en")
    suspend fun getGenres(): Response<GenreResponse>
}