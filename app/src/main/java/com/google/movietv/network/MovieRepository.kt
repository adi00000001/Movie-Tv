package com.google.movietv.network

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.movietv.model.Genre
import com.google.movietv.model.GenreResponse
import com.google.movietv.model.MovieData
import com.google.movietv.model.MovieResponse
import com.google.movietv.ui.adapters.MoviePagingSource
import okio.IOException
import retrofit2.HttpException

class MovieRepository {

    private val movieService = RetrofitClient.ApiClient.apiService

    suspend fun fetchMovies(page: Int): ApiResponse<MovieResponse?> {
        return try {
            val response = movieService.getMovies(page)
            ApiResponse.Success(data = response.body())
        } catch (e: HttpException) {

            ApiResponse.Error(exception = e)

        } catch (e: IOException) {

            ApiResponse.Error(exception = e)
        }
    }

    suspend  fun fetchGenres(): ApiResponse<GenreResponse?> {
        return try {
            val response = movieService.getGenres()
            ApiResponse.Success(data = response.body())
        } catch (e: HttpException) {

            ApiResponse.Error(exception = e)

        } catch (e: IOException) {

            ApiResponse.Error(exception = e)
        }
    }
}