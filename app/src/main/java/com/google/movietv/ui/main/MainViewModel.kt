package com.google.movietv.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.google.android.gms.maps.model.LatLng
import com.google.movietv.model.Cinema
import com.google.movietv.model.MovieData
import com.google.movietv.network.MovieRepository
import com.google.movietv.ui.adapters.MoviePagingSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val moviesRepository = MovieRepository()


    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    //isLoading state with initial value true
    private val _isLoading = MutableStateFlow(true);
    val isLoading = _isLoading.asStateFlow();

    val moviesList: LiveData<PagingData<MovieData>> = getMovies().cachedIn(viewModelScope)
    private val _cineamaList: MutableLiveData<List<Cinema>> = MutableLiveData()
    val cinemaList: LiveData<List<Cinema>> = _cineamaList

    private fun getMovies(): LiveData<PagingData<MovieData>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 200),
        pagingSourceFactory = { MoviePagingSource(moviesRepository) }
    ).liveData

    init {
        initCinemaLocations()
        viewModelScope.launch {
            //Delay to simulate some background processsing like fetching data
            delay(3000)
            //After task is done set isLoading to false to hide splash screen
            _isLoading.value = false
        }
    }

    private fun initCinemaLocations(){
        val russianCinema = Cinema(
            theatreName = "Theatre \"Россия\"",
            LatLng(
                42.87767090813521,
                74.59732026928803
            )
        )
        val cinema2 = Cinema(
            theatreName = "Theatre \"1\"",
            LatLng(
                42.880541450596006, 74.59045285782933
            )
        )
        val cinema3 = Cinema(
            theatreName = "Theatre \"2\"",
            LatLng(
                42.87399887829149, 74.6142039678798
            )
        )
        val cinema4 = Cinema(
            theatreName = "Theatre \"3\"",
            LatLng(
                42.883796826184096, 74.59959447212934
            )
        )
        val cinema5 = Cinema(
            theatreName = "Theatre \"4\"",
            LatLng(
                42.87318492125402, 74.58741989204081
            )
        )
        val cinema6 = Cinema(
            theatreName = "Theatre \"5\"",
            LatLng(
                42.870742985746226, 74.58541215432071
            )
        )
        val cinema7 = Cinema(
            theatreName = "Theatre \"6\"",
            LatLng(
                42.870555140551396, 74.59946631836137
            )
        )
        val list = arrayListOf(
            russianCinema,
            cinema2,
            cinema3,
            cinema4,
            cinema5,
            cinema6,
            cinema7
        )
        _cineamaList.value = list
    }

}