package com.omni.movieappliation.useCases

import android.content.Context
import com.omni.movieappliation.BuildConfig
import com.omni.movieappliation.entities.MoviesResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val MOVIES_BASE_URL = "http://api.themoviedb.org/"
const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/"
const val IMAGE_SIZE = "w185/"
const val DISCOVER_ENDPOINT = "3/movie/top_rated "


val apiServer  :ApiServer by lazy {
    retrofitBuilder(applicationLiveData.getApplication())
}

interface ApiServer {

    @GET(DISCOVER_ENDPOINT)
    fun getMovies(@Query("api_key") apiKey: String = BuildConfig.MOVIE_DB_API_KEY): Call<MoviesResponse>
}


private fun retrofitBuilder(context: Context) =
    Retrofit.Builder()
        .baseUrl(MOVIES_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
//        .client(okHttp(cache(context), appKeyInterceptor(), offlineInterceptor(networkChecker(context))))  cashing part
        .build()
        .create(ApiServer::class.java)
