package com.omni.movieappliation.features.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.omni.movieappliation.R
import com.omni.movieappliation.entities.MovieEntity
import com.omni.movieappliation.features.details.DetailsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main_activity.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MoviesHomeViewModel::class.java) }
    val disposables = CompositeDisposable()

    private val showMovieDetailsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.showMovieDetails.onNext(intent!!.getParcelableExtra(EXTRA_MOVIE))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        bindViews()
        registerReceiver(showMovieDetailsReceiver, IntentFilter(ACTION_SHOW_MOVIES_DETAILS))
    }

    override fun onDestroy() {
        unregisterReceiver(showMovieDetailsReceiver)
        disposables.dispose()
        super.onDestroy()
    }
}

private fun MainActivity.showMessage(message: String) {
    Snackbar.make(coordinator_layout, message, Snackbar.LENGTH_SHORT).show()
    Log.d("callable", message)
}


private fun MainActivity.bindViews() = kotlin.with(viewModel) {

    isLoading.observe(this@bindViews,
        Observer {isLoading-> home_progress_bar.visibility = if (isLoading) View.VISIBLE else View.GONE })


    errorLiveData.observe(this@bindViews,
        Observer { showMessage(it) })

    kotlin.with(home_movies_recycler_view) {
        layoutManager = GridLayoutManager(this@bindViews, 2)
        adapter = MoviesAdapter(this@bindViews , moviesListLiveData)
    }

        showMovieDetails
        .debounce(500, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {movie ->  startDetailsScreen(movie as MovieEntity) }
        .also { disposables.add(it) }

}
private fun MainActivity.startDetailsScreen(movieEntity: MovieEntity) {
    Intent(this, DetailsActivity::class.java)
        .putExtra(EXTRA_MOVIE, movieEntity)
        .also { startActivity(it) }
}


