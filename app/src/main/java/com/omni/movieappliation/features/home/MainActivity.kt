package com.omni.movieappliation.features.home

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProviders
import com.omni.entities.MovieEntity
import com.omni.movieappliation.R
import com.omni.movieappliation.bindViews
import com.omni.movieappliation.features.details.DetailsActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MovieItemClickListener {

    val viewModel by lazy { ViewModelProviders.of(this).get(MoviesHomeViewModel::class.java) }
    val disposables = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        bindViews()
    }

    override fun movieItemClickListener(pos: Int, movie: MovieEntity, imageView: ImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, imageView,
            getString(R.string.image_transition_name)
        )
        val intent = Intent(this, DetailsActivity::class.java)
        intent.apply {
            putExtra(EXTRA_MOVIE, movie)
            putExtra(EXTRA_MOVIE_IMAGE_TRANSITION_NAME, pos)
        }.also { startActivity(it, options.toBundle()) }
    }


    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}




