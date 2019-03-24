package com.omni.movieappliation.useCases

import android.content.Context
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager





fun getImageURL(moviePoster :String , size :String = BIG_IMAGE_SIZE) ="$BASE_IMAGE_URL$size$moviePoster"

 fun isNetworkConnected(context : Context = applicationLiveData.getApplication() ): Boolean {
    var isConnected = false

    try {

        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        isConnected = networkInfo.isConnected
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return isConnected
}