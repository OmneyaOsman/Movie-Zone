package com.omni.movieappliation.features.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieDetailsViewModel : ViewModel(){

    val backDropLiveData  =  MutableLiveData<String>()
    val originalTitleLiveData  =  MutableLiveData<String>()
    val titleLiveData  =  MutableLiveData<String>()
    val overViewLiveData  =  MutableLiveData<String>()
    val posterPathLiveData  =  MutableLiveData<String>()
    val releaseDateLiveData  =  MutableLiveData<String>()
    val popularityDateLiveData  =  MutableLiveData<Double>()
    val voteAverageDateLiveData  =  MutableLiveData<Double>()
    val voteCountDateLiveData  =  MutableLiveData<Int>()

    fun bind(movieEntity: com.omni.entities.MovieEntity){
        originalTitleLiveData.value = movieEntity.original_title
        backDropLiveData.value = movieEntity.backdrop_path
        titleLiveData.value = movieEntity.title
        overViewLiveData.value = movieEntity.overview
        posterPathLiveData.value = movieEntity.poster_path
        releaseDateLiveData.value = movieEntity.release_date
        popularityDateLiveData.value = movieEntity.popularity
        voteAverageDateLiveData.value = movieEntity.vote_average
        voteCountDateLiveData.value = movieEntity.vote_count
    }


}