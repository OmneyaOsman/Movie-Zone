package com.omni.movieappliation.features.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.omni.movieappliation.R
import com.omni.movieappliation.features.home.MoviesHomeViewModel
import com.omni.movieappliation.useCases.BASE_IMAGE_URL
import com.omni.movieappliation.useCases.getImageURL
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    val detailsViewModel by lazy { ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
        bindViews()
    }
}
    private fun DetailsActivity.bindViews() = kotlin.with(detailsViewModel) {

        titleLiveData.observe(this@bindViews,
            Observer {
                toolbar_details.title = it
                toolbar_layout.title = it
            })
        titleLiveData.observe(this@bindViews,
            Observer {
                toolbar_details.title = it
                toolbar_layout.title = it
            })
        releaseDateLiveData.observe(this@bindViews, Observer {
            release_date.text = "Release Date: $it"
        })
        voteAverageDateLiveData.observe(this@bindViews, Observer {
            vote_avg.text = "Vote Avgerage: $it"
        })
        overViewLiveData.observe(this@bindViews, Observer {
            overView.text = it
        })
        posterPathLiveData.observe(this@bindViews, Observer {
            Glide.with(this@bindViews)
                .load(getImageURL(it))
                .into(detail_img_movie)
        })

        backDropLiveData.observe(this@bindViews, Observer {

            Glide.with(this@bindViews)
                .load("${BASE_IMAGE_URL}w500/$it")
                .into(detail_img_cover)
        })

    }

//    val intent = getIntent();
//    if(intent.hasExtra("movie"))
//    {
//        movie = intent.getParcelableExtra("movie");
//
//
//        ;
//
//
//    }
