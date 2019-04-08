package com.omni.movieappliation.features.home

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omni.movieappliation.entities.MovieEntity
import com.omni.movieappliation.entities.MoviesResponse
import com.omni.movieappliation.useCases.engine.toMutableLiveData
import com.omni.movieappliation.useCases.isNetworkConnected
import com.omni.movieappliation.useCases.repositories.moviesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


class MoviesHomeViewModel(
    val showMovieDetails: PublishSubject<Parcelable> = PublishSubject.create(),
    val isLoading: MutableLiveData<Boolean> = false.toMutableLiveData(),
    val errorLiveData: MutableLiveData<String> = MutableLiveData(),
    private val disposables: CompositeDisposable = CompositeDisposable(),
    val topRatedMoviesListLiveData: MutableLiveData<List<MovieEntity>> = ArrayList<MovieEntity>().toMutableLiveData(),
    val popularMoviesListLiveData: MutableLiveData<List<MovieEntity>> = ArrayList<MovieEntity>().toMutableLiveData()

) : ViewModel() {


    init {
        isLoading.postValue(true)
        retrieveMovies()
    }

    private fun retrieveMovies() {
        if (isNetworkConnected()) {
        Observable.zip(moviesRepository.getTopMoviesList().subscribeOn(Schedulers.io()),
            moviesRepository.getPopularMoviesList().subscribeOn(Schedulers.io()) ,
            BiFunction<MoviesResponse, MoviesResponse, Pair<MoviesResponse, MoviesResponse>> { t1, t2 -> Pair(t1, t2) })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pairMoviesResponse ->
                isLoading.postValue(false)
                pairMoviesResponse?.let {
                    topRatedMoviesListLiveData.postValue(it.first.results)
                    popularMoviesListLiveData.postValue(it.second.results)
                }
            }, { error ->
                isLoading.postValue(false)
                errorLiveData.postValue(error.message)

            })?.also { disposable -> disposables.add(disposable) }
    } else {
        isLoading.postValue(false)
        errorLiveData.postValue("Internet connection error")
    }

//        if (isNetworkConnected()) {
//            moviesRepository.getTopMoviesList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ moviesResponse ->
//                    isLoading.postValue(false)
//                    moviesResponse?.let { response ->
//                        topRatedMoviesListLiveData.postValue(response.results)
//                    }
//                }, { error ->
//                    isLoading.postValue(false)
//                    errorLiveData.postValue(error.message)
//
//                })?.also { disposable -> disposables.add(disposable) }
//        } else {
//            isLoading.postValue(false)
//            errorLiveData.postValue("Internet connection error")
//        }
    }


    override fun onCleared() {
        super.onCleared()
        showMovieDetails.onComplete()
        disposables.dispose()
    }
}