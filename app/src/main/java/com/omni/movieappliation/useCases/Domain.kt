package com.omni.movieappliation.useCases

import android.app.Application
import androidx.lifecycle.MutableLiveData



internal val applicationLiveData = MutableLiveData<Application>()

internal fun MutableLiveData<Application>.getApplication(): Application = value!!

object Domain {

    fun integrateWith(application: Application) {

        applicationLiveData.value = application
    }

}