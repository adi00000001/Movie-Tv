package com.google.movietv.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.movietv.databinding.FragmentMovieListBinding
import com.google.movietv.ui.adapters.MovieLoadStateAdapter
import com.google.movietv.ui.adapters.MoviePagingAdapter
import com.google.movietv.ui.hide
import com.google.movietv.ui.main.MainViewModel
import com.google.movietv.ui.show

class MovieListFragment : Fragment(){

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private var movieListAdapter = MoviePagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpListeners()
        observeViewModel()
    }
    private fun setUpViews() = with(binding){
        rvMovieList.adapter = movieListAdapter
        setUpLoadStateListener()
    }
    private fun setUpListeners() = with(binding) {

    }

    private fun observeViewModel() {
        viewModel.moviesList.observe(viewLifecycleOwner) {
            movieListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.error.observe(viewLifecycleOwner) {

        }
    }

    private fun setUpLoadStateListener() = with(binding) {
        movieListAdapter.addLoadStateListener { loadState ->

            when{
                loadState.refresh is LoadState.Loading -> {
                    progressCenter.show()
                }
                loadState.append is LoadState.Loading -> {
                    progressBottom.show()
                }
                else -> {
                    progressBottom.hide()
                    progressCenter.hide()
                }
            }

        }

        rvMovieList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieListAdapter.withLoadStateHeaderAndFooter(
                footer = MovieLoadStateAdapter { movieListAdapter.retry()},
                header = MovieLoadStateAdapter { movieListAdapter.retry()}
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}