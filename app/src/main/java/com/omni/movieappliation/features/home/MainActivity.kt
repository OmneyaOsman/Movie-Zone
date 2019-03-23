package com.omni.movieappliation.features.home

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.omni.movieappliation.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MoviesHomeViewModel::class.java) }
    lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        bindViews()
    }
}

private fun MainActivity.showMessage(message: String) {
    Snackbar.make(coordinator_layout, message, Snackbar.LENGTH_SHORT).show()
}

private fun MainActivity.bindViews() = kotlin.with(viewModel) {

    isLoading.observe(this@bindViews,
        Observer { home_progress_bar.visibility = if (it) View.VISIBLE else View.GONE })


    errorLiveData.observe(this@bindViews,
        Observer { showMessage(it) })

    kotlin.with(home_movies_recycler_view) {
        layoutManager = GridLayoutManager(this@bindViews, 2)
        adapter = MoviesAdapter()
    }

    moviesListLiveData.observe(this@bindViews,
        Observer { if (it.isNotEmpty()) adapter.updateMoviesList(it) })


}
