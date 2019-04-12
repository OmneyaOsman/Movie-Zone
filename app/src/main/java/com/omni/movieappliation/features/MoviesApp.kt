package com.omni.movieappliation.features

import android.app.Application

class MoviesApp :Application(){

    override fun onCreate() {
        super.onCreate()
        com.omni.domain.Domain.integrateWith(this)
    }
}