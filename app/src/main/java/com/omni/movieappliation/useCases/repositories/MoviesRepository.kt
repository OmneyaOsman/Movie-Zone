package com.omni.movieappliation.useCases.repositories

import com.omni.movieappliation.useCases.ApiServer
import com.omni.movieappliation.useCases.apiServer


val moviesRepository  :MoviesRepository by lazy {
    MoviesRepository()
}

class MoviesRepository(private val api: ApiServer = apiServer){

    fun getMoviesList() = api.getMovies()

}