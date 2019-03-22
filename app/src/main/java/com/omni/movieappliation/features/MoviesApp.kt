package com.omni.movieappliation.features

import android.app.Application
import com.omni.movieappliation.useCases.Domain

class MoviesApp :Application(){

    override fun onCreate() {
        super.onCreate()
        Domain.integrateWith(this)
    }
}