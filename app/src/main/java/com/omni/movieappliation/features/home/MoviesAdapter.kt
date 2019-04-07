package com.omni.movieappliation.features.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.omni.movieappliation.entities.MovieEntity
import com.omni.movieappliation.useCases.getImageURL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item.view.*


const val ACTION_SHOW_MOVIES_DETAILS = "ACTION_SHOW_MOVIES_DETAILS"
const val EXTRA_MOVIE = "EXTRA_MOVIE"
const val EXTRA_MOVIE_IMAGE_TRANSITION_NAME = "EXTRA_MOVIE_IMAGE_TRANSITION_NAME"

interface MovieItemClickListener {
    fun movieItemClickListener(pos: Int, movie: MovieEntity, imageView: ImageView)
}

class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val imageView by lazy {
        view.image_item_list
    }

    fun bind(movie: MovieEntity, pos: Int, movieItemClickListener: MovieItemClickListener) {
        Picasso.get()
            .load(getImageURL(movie.poster_path))
            .into(imageView)

        ViewCompat.setTransitionName(imageView, EXTRA_MOVIE_IMAGE_TRANSITION_NAME)

        view.setOnClickListener {
            movieItemClickListener.movieItemClickListener(pos, movie, imageView)

        }
    }

}

class MoviesAdapter(
    lifecycleOwner: LifecycleOwner,
    private val moviesList: MutableLiveData<List<MovieEntity>>,
    private val movieItemClickListener: MovieItemClickListener
) : RecyclerView.Adapter<ViewHolder>() {


    init {
        moviesList.observe(lifecycleOwner, Observer {
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(com.omni.movieappliation.R.layout.movie_list_item, parent, false)
            .let { ViewHolder(it) }
    }

    override fun getItemCount(): Int {
        return moviesList.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moviesList.value!![position], position, movieItemClickListener)
    }

}

