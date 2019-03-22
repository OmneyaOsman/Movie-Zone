package com.omni.movieappliation.feature

import android.app.Application
import com.omni.movieappliation.useCases.Domain

class MoviesApp :Application(){

    override fun onCreate() {
        super.onCreate()
        Domain.integrateWith(this)
    }
}