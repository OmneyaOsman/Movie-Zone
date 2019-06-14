package com.omni.movieappliation.features.home

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omni.domain.engine.toMutableLiveData
import com.omni.domain.networkChecker
import com.omni.domain.repositories.moviesRepository
import com.omni.entities.MovieEntity
import com.omni.entities.MoviesResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


enum class MoviesApiStatus { LOADING, ERROR, DONE }

class MoviesHomeViewModel(
    val showMovieDetails: PublishSubject<Parcelable> = PublishSubject.create(),
    val status: MutableLiveData<MoviesApiStatus> = MutableLiveData(),
    val errorLiveData: MutableLiveData<String> = MutableLiveData(),
    private val disposables: CompositeDisposable = CompositeDisposable(),
    val topRatedMoviesListLiveData: MutableLiveData<List<MovieEntity>> = ArrayList<MovieEntity>().toMutableLiveData(),
    val popularMoviesListLiveData: MutableLiveData<List<MovieEntity>> = ArrayList<MovieEntity>().toMutableLiveData()
) : ViewModel() {


    init {
        status.postValue(MoviesApiStatus.LOADING)
        retrieveMovies()
    }

    private fun retrieveMovies() {

        if (!networkChecker()) {
            status.postValue(MoviesApiStatus.ERROR)
            errorLiveData.postValue("Internet connection error")
        }
        Observable.zip(
            moviesRepository.getTopMoviesList().subscribeOn(Schedulers.io()),
            moviesRepository.getPopularMoviesList().subscribeOn(Schedulers.io()),
            BiFunction<MoviesResponse, MoviesResponse, Pair<MoviesResponse, MoviesResponse>> { t1, t2 ->
                Pair(
                    t1,
                    t2
                )
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pairMoviesResponse ->
                status.postValue(MoviesApiStatus.DONE)
                pairMoviesResponse?.let { pair ->
                    topRatedMoviesListLiveData.postValue(pair.first.results)
                    popularMoviesListLiveData.postValue(pair.second.results)
                }
            }, { error ->
                status.postValue(MoviesApiStatus.ERROR)
                errorLiveData.postValue(error.message)

            })?.also { disposable -> disposables.add(disposable) }


    }


    override fun onCleared() {
        super.onCleared()
        showMovieDetails.onComplete()
        disposables.dispose()
    }
}