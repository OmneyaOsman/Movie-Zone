package com.omni.movieappliation.features.home

import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omni.movieappliation.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main_activity.*


class MainActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MoviesHomeViewModel::class.java) }
    lateinit var movieAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        movieAdapter = MoviesAdapter()
        bindViews()
    }
}

private fun MainActivity.showMessage(message: String) {
    Snackbar.make(coordinator_layout, message, Snackbar.LENGTH_SHORT).show()
    Log.d("callable", message)
}

private fun MainActivity.bindViews() = kotlin.with(viewModel) {

    isLoading.observe(this@bindViews,
        Observer { home_progress_bar.visibility = if (it) View.VISIBLE else View.GONE })


    errorLiveData.observe(this@bindViews,
        Observer { showMessage(it) })

    kotlin.with(home_movies_recycler_view) {
        layoutManager = GridLayoutManager(this@bindViews, 2)
        adapter = movieAdapter
    }

    moviesListLiveData.observe(this@bindViews,
        Observer {
            movieAdapter.updateMoviesList(it)
            Log.d("callable", movieAdapter.itemCount.toString())
            Log.d("callable", movieAdapter.moviesList[1].poster_path)
        })


}
