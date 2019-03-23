package com.omni.movieappliation.features.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omni.movieappliation.entities.MovieEntity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.omni.movieappliation.useCases.getImageURL
import kotlinx.android.synthetic.main.movie_list_item.view.*


class MoviesAdapter() : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private lateinit var moviesList: List<MovieEntity>



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: View = layoutInflater.inflate(com.omni.movieappliation.R.layout.movie_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (::moviesList.isInitialized) moviesList.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moviesList[position])
        holder.itemView.setOnClickListener {

        }
    }

    fun updateMoviesList(moviesList: List<MovieEntity>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        private val imageView: ImageView


        init {
            imageView = itemView.image_item_list
        }


        fun bind(movie: MovieEntity) {
            Glide.with(imageView.context)
                .load(getImageURL(movie.poster_path))
                .into(imageView)
        }

    }
}

