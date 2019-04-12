package com.omni.domain

import android.content.Context
import android.net.ConnectivityManager





fun getImageURL(moviePoster :String , size :String = BIG_IMAGE_SIZE) ="$BASE_IMAGE_URL$size$moviePoster"

