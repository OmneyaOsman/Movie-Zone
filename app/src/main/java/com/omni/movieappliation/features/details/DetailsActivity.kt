package com.omni.movieappliation.features.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.omni.entities.MovieEntity
import com.omni.movieappliation.R
import com.omni.movieappliation.bindViews
import com.omni.movieappliation.features.home.EXTRA_MOVIE
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    val detailsViewModel by lazy { ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar_details)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val intent = intent
        if (intent.hasExtra(EXTRA_MOVIE))
            intent?.let {
                it.getParcelableExtra<MovieEntity>(EXTRA_MOVIE)?.let{movie->
                    detailsViewModel.bind(movie)
                }

            }


        bindViews()

    }
}



