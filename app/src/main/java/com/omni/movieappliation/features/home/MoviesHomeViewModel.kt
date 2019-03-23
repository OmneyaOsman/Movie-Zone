package com.omni.movieappliation.features.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omni.movieappliation.R
import com.omni.movieappliation.entities.MovieEntity
import com.omni.movieappliation.useCases.applicationLiveData
import com.omni.movieappliation.useCases.getApplication
import com.omni.movieappliation.useCases.repositories.moviesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MoviesHomeViewModel : ViewModel() {

    private val disposable: Disposable
    val isLoading = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val moviesListLiveData = MutableLiveData<List<MovieEntity>>()


    init {
        isLoading.postValue(true)
        errorLiveData.postValue(applicationLiveData.getApplication().getString(R.string.empty_view))
        val moviesObservable = Observable.fromCallable { moviesRepository.getMoviesList() }
        disposable = moviesObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe({ moviesResponse ->
                isLoading.postValue(false)
                moviesResponse?.takeUnless { it.results.isEmpty() }?.let {
                    moviesListLiveData.postValue(it.results)
                }
            }, { error ->
                isLoading.postValue(false)
                errorLiveData.postValue(error.message)

            })
    }


    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed)
            disposable.dispose()
    }
}