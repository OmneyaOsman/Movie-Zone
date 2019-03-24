package com.omni.movieappliation.features.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omni.movieappliation.R
import com.omni.movieappliation.entities.MovieEntity
import com.omni.movieappliation.entities.MoviesResponse
import com.omni.movieappliation.useCases.applicationLiveData
import com.omni.movieappliation.useCases.getApplication
import com.omni.movieappliation.useCases.isNetworkConnected
import com.omni.movieappliation.useCases.repositories.moviesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MoviesHomeViewModel : ViewModel() {

    private lateinit var disposable: Disposable
    val isLoading = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val moviesListLiveData = MutableLiveData<List<MovieEntity>>()


    init {
        isLoading.postValue(true)
        if (isNetworkConnected()) {
            val moviesObservable = Observable.fromCallable<MoviesResponse> { moviesRepository.getMoviesList().execute().body() }
            disposable = moviesObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ moviesResponse ->
                    isLoading.postValue(false)
                    errorLiveData.postValue("")
                    moviesResponse?.let {
                        moviesListLiveData.postValue(moviesResponse.results)
                    }
                }, { error ->
                    isLoading.postValue(false)
                    errorLiveData.postValue(error.message)

                })
        } else {
            isLoading.postValue(false)
            errorLiveData.postValue("Internet connection error")
        }
    }


    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed)
            disposable.dispose()
    }
}