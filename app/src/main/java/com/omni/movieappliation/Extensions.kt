package com.omni.movieappliation

import android.content.Intent
import android.view.View.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.omni.entities.MovieEntity
import com.omni.movieappliation.core.CropSquareTransformation
import com.omni.movieappliation.features.details.DetailsActivity
import com.omni.movieappliation.features.home.EXTRA_MOVIE
import com.omni.movieappliation.features.home.MainActivity
import com.omni.movieappliation.features.home.MoviesAdapter
import com.omni.movieappliation.features.home.MoviesApiStatus
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main_activity.*
import java.util.concurrent.TimeUnit


fun MainActivity.showMessage(message: String) {
    Snackbar.make(coordinator_layout, message, Snackbar.LENGTH_SHORT).show()
}

fun MainActivity.bindStatus(status: MoviesApiStatus?) {
    when (status) {
        MoviesApiStatus.LOADING -> {
            status_image.visibility = VISIBLE
            popular_movies_title.visibility = INVISIBLE
            top_rated_title.visibility = INVISIBLE
            status_image.setImageResource(R.drawable.loading_animation)
        }
        MoviesApiStatus.ERROR -> {
            status_image.visibility = VISIBLE
            popular_movies_title.visibility = INVISIBLE
            top_rated_title.visibility = INVISIBLE
            status_image.setImageResource(R.drawable.ic_connection_error)
        }
        MoviesApiStatus.DONE -> {
            status_image.visibility = GONE
            popular_movies_title.visibility = VISIBLE
            top_rated_title.visibility = VISIBLE
        }
    }
}

fun MainActivity.bindViews() = with(viewModel) {

    status.observe(this@bindViews,
        Observer { bindStatus(it) })


    errorLiveData.observe(this@bindViews,
        Observer { showMessage(it) })

    with(home_movies_recycler_view) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this@bindViews, LinearLayoutManager.HORIZONTAL, false)
        adapter = MoviesAdapter(this@bindViews, topRatedMoviesListLiveData, this@bindViews)
    }
    with(popular_movies_recycler_view) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this@bindViews, LinearLayoutManager.HORIZONTAL, false)
        adapter = MoviesAdapter(this@bindViews, popularMoviesListLiveData, this@bindViews)
    }

    showMovieDetails
        .debounce(500, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { movie -> startDetailsScreen(movie as MovieEntity) }
        .also { disposables.add(it) }

}!!


private fun MainActivity.startDetailsScreen(movieEntity: MovieEntity) {
    Intent(this, DetailsActivity::class.java)
        .putExtra(EXTRA_MOVIE, movieEntity)
        .also { startActivity(it) }
}


fun DetailsActivity.bindViews() = with(detailsViewModel) {

    titleLiveData.observe(this@bindViews,
        Observer {
            toolbar_details.title = it
            toolbar_layout.title = it
        })

    releaseDateLiveData.observe(this@bindViews, Observer {
        release_date.text = "Release Date: $it"
    })
    voteAverageDateLiveData.observe(this@bindViews, Observer {
        vote_avg.text = "$it"
    })
    overViewLiveData.observe(this@bindViews, Observer {
        overView.text = it
    })
    posterPathLiveData.observe(this@bindViews, Observer {
        Picasso.get()
            .load(com.omni.domain.getImageURL(it, com.omni.domain.IMAGE_SIZE))
            .transform(CropSquareTransformation(10, 0))
            .noFade()
            .into(detail_img_movie, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }

            })
    })

    backDropLiveData.observe(this@bindViews, Observer {
        Picasso.get()
            .load(com.omni.domain.getImageURL(it))
            .into(detail_img_cover)
    })

}