package com.omni.movieappliation.core

import android.content.Context
import android.net.ConnectivityManager
import com.omni.domain.applicationLiveData
import com.omni.domain.getApplication



 fun networkChecker(context: Context = applicationLiveData.getApplication()): Boolean =
    context.getSystemService(Context.CONNECTIVITY_SERVICE)
        ?.let { it as ConnectivityManager }
        ?.activeNetworkInfo
        ?.isConnected
        ?: false

