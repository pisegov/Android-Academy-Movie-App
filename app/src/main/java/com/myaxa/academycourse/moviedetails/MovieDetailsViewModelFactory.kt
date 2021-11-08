package com.myaxa.academycourse.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myaxa.academycourse.data.MovieRepository

class MovieDetailsViewModelFactory(
    private val repository: MovieRepository,
    private val movieId: Int,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MovieDetailsViewModel(repository, movieId) as T
}