package com.omni.domain

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.omni.entities.MoviesResponse
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val MOVIES_BASE_URL = "http://api.themoviedb.org/"
const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p"
const val IMAGE_SIZE = "/w185"
const val BIG_IMAGE_SIZE = "/w500"
const val TOP_RATED_ENDPOINT = "3/movie/top_rated "
const val POPULAR_MOVIES_ENDPOINT = "3/movie/popular "


val apiServer: ApiServer by lazy {
    retrofitBuilder()
}

//Get Upcoming
//GET
///movie/upcoming

//Get Now Playing
//GET
///movie/now_playing

//Get Upcoming
//GET
///movie/upcoming
//Get Similar Movies
//GET
///movie/{movie_id}/similar

//crew
//https://api.themoviedb.org/3/movie/150540?api_key=ceb888b71023afda704f84975d2642b5&append_to_response=credits

interface ApiServer {

    @GET(TOP_RATED_ENDPOINT)
    fun getTopRatedMovies(@Query("api_key") apiKey: String = BuildConfig.MOVIE_DB_API_KEY): Observable<MoviesResponse>
    @GET(POPULAR_MOVIES_ENDPOINT)
    fun getPopularMovies(@Query("api_key") apiKey: String = BuildConfig.MOVIE_DB_API_KEY): Observable<MoviesResponse>
}


fun networkChecker(context: Context = applicationLiveData.getApplication()): Boolean =
    context.getSystemService(Context.CONNECTIVITY_SERVICE)
        ?.let { it as ConnectivityManager }
        ?.activeNetworkInfo
        ?.isConnected
        ?: false
private const val cacheSize = (5 * 1024 * 1024).toLong()

private fun getCash(applicationContext: Application = applicationLiveData.getApplication())
        =  Cache(applicationContext.cacheDir, cacheSize)

private fun getInterceptor() = Interceptor { chain ->
    var request = chain.request()
    request = if (networkChecker())
        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
    else
        request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
    chain.proceed(request)
}

private fun getOkHttpClient()= OkHttpClient.Builder().cache(getCash()).addInterceptor(getInterceptor() ).build()

private fun retrofitBuilder(): ApiServer {

//    val loggingInterceptor = HttpLoggingInterceptor()
//    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//    val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    return Retrofit.Builder()
        .baseUrl(MOVIES_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build()
        .create(ApiServer::class.java)
}


