package com.omni.movieappliation.features.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omni.movieappliation.R
import com.omni.movieappliation.features.home.EXTRA_MOVIE
import com.omni.movieappliation.useCases.IMAGE_SIZE
import com.omni.movieappliation.useCases.getImageURL
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    val detailsViewModel by lazy { ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar_details)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val intent = intent
        if (intent.hasExtra(EXTRA_MOVIE))
            intent?.let {
                detailsViewModel.bind(it.getParcelableExtra(EXTRA_MOVIE))
            }


        bindViews()

    }
}

private fun DetailsActivity.bindViews() = kotlin.with(detailsViewModel) {

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
        Picasso.get()
            .load(getImageURL(it, IMAGE_SIZE))
            .into(detail_img_movie)
    })

    backDropLiveData.observe(this@bindViews, Observer {
        Picasso.get()
            .load(getImageURL(it))
            .noFade()
            .into(detail_img_cover, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }

            })
    })

}

