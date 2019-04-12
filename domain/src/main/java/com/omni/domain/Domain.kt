package com.omni.domain

import android.app.Application
import androidx.lifecycle.MutableLiveData



 val applicationLiveData = MutableLiveData<Application>()

 fun MutableLiveData<Application>.getApplication(): Application = value!!

object Domain {

    fun integrateWith(application: Application) {

        applicationLiveData.value = application
    }

}