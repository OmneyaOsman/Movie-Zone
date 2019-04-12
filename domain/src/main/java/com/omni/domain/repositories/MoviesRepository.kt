package com.omni.domain.repositories

import com.omni.domain.ApiServer
import com.omni.domain.apiServer


val moviesRepository  : MoviesRepository by lazy {
    MoviesRepository()
}

class MoviesRepository(private val api: ApiServer = apiServer){

    fun getTopMoviesList() = api.getTopRatedMovies()
    fun getPopularMoviesList() = api.getPopularMovies()

}