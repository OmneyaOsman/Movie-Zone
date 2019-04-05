package com.omni.movieappliation.features.splash

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.omni.movieappliation.R
import com.omni.movieappliation.features.home.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setBackgroundAnimation()
        createTimer()
    }

    private fun createTimer() {
        disposable = Observable.timer(2000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { launchMainActivity() }
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}

private fun SplashActivity.setBackgroundAnimation() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

    val animDrawable = splash_layout
        .background as AnimationDrawable
    animDrawable.setEnterFadeDuration(10)
    animDrawable.setExitFadeDuration(5000)
    animDrawable.start()
}

private fun SplashActivity.launchMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
}
