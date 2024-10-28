package com.google.movietv.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.movietv.databinding.ItemMovieBinding
import com.google.movietv.model.MovieData
import com.google.movietv.network.RetrofitClient.BASE_URL_IMAGE
import com.google.movietv.ui.loadImage
import com.google.movietv.ui.round

class MoviePagingAdapter : PagingDataAdapter<MovieData, MoviePagingAdapter.ViewHolder>(MovieDiffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieData?)= with(binding){
            item?.let {
                tvFilmTitle.text = it.title
                tvFilmRating.text = it.voteAverage.round().toString()
                tvFilmDescription.text = it.overview
                tvReleaseDate.text = it.releaseDate
                val imagePoster = BASE_URL_IMAGE + it.posterPath
                ivPoster.loadImage(imagePoster)
            }

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
    object MovieDiffCallback : DiffUtil.ItemCallback<MovieData>() {
        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem == newItem
        }
    }
}