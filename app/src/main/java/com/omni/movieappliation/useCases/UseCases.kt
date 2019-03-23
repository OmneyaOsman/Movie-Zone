package com.omni.movieappliation.useCases

import com.omni.movieappliation.features.home.MoviesAdapter


fun MoviesAdapter.ViewHolder.getImageURL(moviePoster :String) ="$IMAGE_SIZE$BASE_IMAGE_URL$moviePoster"

