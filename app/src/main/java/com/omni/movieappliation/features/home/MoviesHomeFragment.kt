package com.omni.movieappliation.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omni.movieappliation.R

class MoviesHomeFragment : Fragment(){

    val viewModel by lazy { ViewModelProviders.of(this).get(MoviesHomeViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment_layout , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }
 }

private fun MoviesHomeFragment.bindViews() = with(viewModel) {

//    isLoading.observe(this@bindViews,
//        Observer { home_progress_bar.visibility = if (it) VISIBLE else GONE })
//
//    home_search_editText.withTextWatcher(searchInput::setValue)
//
//
//    with(home_movies_recycler_view) {
//        layoutManager = LinearLayoutManager(this@bindViews)
//        adapter = SearchResultsAdapter(searchResult, this@bindViews)
//        setOnTouchListener { _, _ -> hideKeyboard() }
//    }

}
