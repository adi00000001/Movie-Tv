package com.google.movietv.ui.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.movietv.model.MovieData
import com.google.movietv.network.ApiResponse
import com.google.movietv.network.MovieRepository

class MoviePagingSource(
    private val repository: MovieRepository,
) : PagingSource<Int, MovieData>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        return try {
            val position = params.key ?: 1
            when (val response = repository.fetchMovies(position)) {
                is ApiResponse.Success -> {
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else (position - 1),
                        nextKey = if (position == response.data.totalPages) null else (position + 1)
                    )
                }

                else -> {
                    LoadResult.Error(throw Exception("No Response"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}